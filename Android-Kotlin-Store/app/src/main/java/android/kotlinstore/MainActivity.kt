package android.kotlinstore

import android.content.res.ColorStateList
import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Reducers.App.DialogState
import android.kotlinstore.Store.Store
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.powerlifting.powerliftingjournalfirebase.Effects.UserEffects
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : StoreActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        var instance:MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        nav_view.menu.getItem(0)?.isChecked = true
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        //No call for super(). Bug on API Level > 11.
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home ->Store.dispatch(AppReducer.SET_HOME_PAGE_ACTION())
            R.id.nav_settings ->Store.dispatch(AppReducer.SET_SETTINGS_PAGE_ACTION())
            R.id.nav_share -> {}
            R.id.nav_logout->
                Store.dispatch(AppReducer.SET_DIALOG_ACTION(DialogState(
                        title = "Logout",
                        message = "Are you sure you want to Logout?",
                        callBack = {
                            FirebaseAuth.getInstance().signOut()
                            Store.dispatch(UserEffects.instance.updateUserInfo())
                        }
                )))
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()
        setupStore()
        Store.dispatch(AppReducer.SET_LOGIN_PAGE_ACTION())
        Store.dispatch(UserEffects.instance.updateUserInfo())
        runOnUiThread({
            fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorPrimary))
        })
    }
}
