package android.kotlinstore.Effects

import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Reducers.App.DrawState
import android.kotlinstore.Store.Effect
import android.kotlinstore.Store.Store
import android.kotlinstore.Store.getState

class NavMenuBtnEffects { companion object {
        val instance: NavMenuBtnEffects by lazy { Holder.INSTANCE }
    }
    private object Holder { val INSTANCE = NavMenuBtnEffects() }

    fun click() : Effect {
        return Effect(arrayListOf(
                fun(state): Thread? {
                    val pageState = getState<AppReducer.State>(AppReducer.name).page
                    return when (pageState.selectedPage) {
                        AppReducer.HOME_PAGE -> Store.dispatch(AppReducer.SET_DRAW_ACTION(DrawState(isOpen = true)))
                        AppReducer.SETTING_PAGE -> Store.dispatch(AppReducer.SET_DRAW_ACTION(DrawState(isOpen = true)))
                        AppReducer.ADD_TO_DAY_PAGE -> Store.dispatch(AppReducer.SET_HOME_PAGE_ACTION())
                        else -> null
                    }
                }
        ))
    }
}