package android.kotlinstore.Effects

import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Store.Effect
import android.kotlinstore.Store.Store
import android.kotlinstore.Store.getState

class AddBtnEffects {
    companion object {
        val instance: AddBtnEffects by lazy { Holder.INSTANCE }
    }
    private object Holder { val INSTANCE = AddBtnEffects() }

    fun click() : Effect {
        return Effect(arrayListOf(
                fun(state): Thread? {
                    val pageState = getState<AppReducer.State>(AppReducer.name).page
                    return when (pageState.selectedPage) {
                        AppReducer.HOME_PAGE -> Store.dispatch(AppReducer.SET_ADD_TO_DAY_PAGE_ACTION())
                        AppReducer.ADD_TO_DAY_PAGE -> this@AddBtnEffects.addToDay()
                        else -> null
                    }
                }
        ))
    }

    fun addToDay() : Thread {
        return Store.dispatch(AppReducer.SET_HOME_PAGE_ACTION())
    }
}