package android.kotlinstore

import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Store.Store
import org.junit.Assert.assertEquals
import org.junit.Test

class StoreTest {
    constructor() {
        Store.addReducer(AppReducer.name, AppReducer())
    }

   @Test
    fun isStateCreateAfterAddingReducers() {
        assertEquals(Store.state.size, 1)
    }
}
