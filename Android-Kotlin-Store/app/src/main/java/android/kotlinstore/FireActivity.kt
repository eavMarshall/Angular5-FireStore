package android.kotlinstore

import android.content.Intent
import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Reducers.App.SnackState
import android.kotlinstore.Store.Store
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.powerlifting.powerliftingjournalfirebase.Effects.UserEffects

/**s
 * Trying to keep all the fire auth/store activity related stuff here
 */
abstract class FireActivity: AppCompatActivity() {
    companion object {
        var db: FirebaseFirestore? = null
            private set

        fun initFirestore(): Boolean {
            if (null == db) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (!(uid == "" || uid == null)) {
                    val fireStore = FirebaseFirestore.getInstance()
                    // TODO do something else here too?
                    db = fireStore

                    return true
                }
            }
            return false
        }
    }
    private val RC_SIGN_IN = 9001

    fun signIn() {
        if (null == FirebaseAuth.getInstance().currentUser?.uid || "" == FirebaseAuth.getInstance().currentUser?.uid) {
            Store.dispatch(AppReducer.SET_LOADING_ACTION(true))
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    //Leave this as default_web_client_id the values will come from google-services.json file
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        } else {
            Store.dispatch(AppReducer.SET_HOME_PAGE_ACTION())
            Store.dispatch(AppReducer.SET_LOADING_ACTION(false))
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                firebaseAuthWithGoogle(task.getResult(ApiException::class.java))
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Store.dispatch(AppReducer.SEND_SNACKBAR_MESSAGE_ACTION(
                        SnackState(message = "Authenticate failed with code:${e.statusCode}", isError = true)))
                Store.dispatch(AppReducer.SET_LOGIN_PAGE_ACTION())
                Store.dispatch(AppReducer.SET_LOADING_ACTION(false))
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        FirebaseAuth.getInstance()
                .signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))
                .addOnCompleteListener(this, { task ->
                    if (!task.isSuccessful) {
                        Store.dispatch(AppReducer.SEND_SNACKBAR_MESSAGE_ACTION(
                                SnackState(message = "Authenticate failed", isError = true)))
                        Store.dispatch(AppReducer.SET_LOGIN_PAGE_ACTION())
                        Store.dispatch(AppReducer.SET_LOADING_ACTION(false))
                    }
                    Store.dispatch(UserEffects.instance.updateUserInfo())
                })
    }
}