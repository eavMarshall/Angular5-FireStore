package com.powerlifting.powerliftingjournalfirebase.Effects

import android.kotlinstore.FireActivity
import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Reducers.App.UserState
import android.kotlinstore.Store.Effect
import android.kotlinstore.Store.Store
import android.kotlinstore.Store.getState
import com.google.firebase.auth.FirebaseAuth

class UserEffects {
    companion object {
        val instance: UserEffects by lazy { Holder.INSTANCE }
    }
    private object Holder { val INSTANCE = UserEffects() }

    fun updateUserInfo() : Effect {
        return Effect(arrayListOf(
                fun(state): Thread? {
                    var firebaseAuth = FirebaseAuth.getInstance()
                    if (null != firebaseAuth.currentUser?.uid && firebaseAuth.currentUser?.uid != "") {
                        FireActivity.initFirestore()

                        return Store.dispatch(AppReducer.SET_USER_ACTION(UserState(
                                uid = "" + firebaseAuth.currentUser?.uid,
                                displayName = "" + firebaseAuth.currentUser?.displayName,
                                email = "" + firebaseAuth.currentUser?.email,
                                photoUrl = "" + firebaseAuth.currentUser?.photoUrl
                        )))
                    } else {
                        return Store.dispatch(AppReducer.SET_USER_ACTION(UserState()))
                    }
                },
                fun(state): Thread? {
                    val userState = getState<AppReducer.State>(AppReducer.name).user
                    return Store.dispatch(when (userState.uid) { ""-> AppReducer.SET_LOGIN_PAGE_ACTION() else-> AppReducer.SET_HOME_PAGE_ACTION() })
                },
                fun(state): Thread? {
                    return Store.dispatch(AppReducer.SET_LOADING_ACTION(false))
                }
        ))
    }
}