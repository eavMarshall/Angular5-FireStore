package android.kotlinstore.Reducers.AddToDay

import android.kotlinstore.Store.Action
import android.kotlinstore.Store.Reducer

/**
 *
 */
class AddToDayReducer : Reducer() {
    companion object {
        val name = "AddToDayReducer"

        private val reducerName = "[$name:] "

        private val ADD_TO_DAY: String = reducerName + "add text to selected day"
    }

    data class State (
            var text: String
    )

    override fun reduce(storeState: Any?, action: Action):Any? {
        return storeState
    }
}