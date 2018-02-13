package android.kotlinstore

import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Reducers.App.DialogState
import android.kotlinstore.Reducers.App.ToolbarState
import android.kotlinstore.Reducers.App.UserState
import android.kotlinstore.Store.Store
import android.kotlinstore.Store.getState
import android.kotlinstore.Store.waitForThread
import org.junit.Assert.assertEquals
import org.junit.Test

class AppReducerTest {
    constructor() {
        Store.addReducer(AppReducer.name, AppReducer())
    }

    @Test
    fun test_blank_toolbar_state() {
        var toolbarState = ToolbarState()
        assertEquals(toolbarState.title, "KotlinState")
        assertEquals(toolbarState.showToolbar, false)
        assertEquals(toolbarState.showBackBtn, false)
    }

    @Test
    fun test_blank_DialogState() {
        var dialogState = DialogState()
        assertEquals(dialogState.title, "")
        assertEquals(dialogState.message, "")
        assertEquals(dialogState.isError, false)
    }

    @Test
    fun test_DialogState_callback() {
        var dialogState = DialogState(callBack = {})
        assertEquals(dialogState.callBack(), Unit)
    }

    @Test
    fun testBlankUserState() {
        val blank = UserState()
        assertEquals(blank.uid, "")
        assertEquals(blank.displayName, "Anonymous")
        assertEquals(blank.email, "")
    }

    val testinguid = "testinguid123"
    val testingName = "testing name"
    val testingEmail = "testing@email.com"

    @Test
    fun testUserReducer_UPDATE_USER() {
        waitForThread(Store.dispatch(
                AppReducer.SET_USER_ACTION(
                        UserState(uid = testinguid, displayName = testingName, email = testingEmail)
                )
        ))

        var userState = getState<AppReducer.State>(AppReducer.name).user
        assertEquals(userState.uid, testinguid)
        assertEquals(userState.displayName, testingName)
        assertEquals(userState.email, testingEmail)
    }
}
