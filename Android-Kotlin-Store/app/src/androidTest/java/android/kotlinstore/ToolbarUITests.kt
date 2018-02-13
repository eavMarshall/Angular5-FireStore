package android.kotlinstore

import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Store.Action
import android.kotlinstore.Store.Store
import android.kotlinstore.Store.waitForThread
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 *
 */
@RunWith(AndroidJUnit4::class)
class ToolbarUITests {
    var mainActivity = ActivityTestRule(MainActivity::class.java, true, true)
    @Rule get

    @Test
    fun Default_Toolbar_VISIBLE() {
        val toolbar = onView(withId(R.id.toolbar))
        toolbar.check { view, noViewFoundException ->
            assertEquals(View.VISIBLE, view.visibility)
        }
    }

    @Test
    fun ActivityReducer_SET_LOGIN_PAGE_TOOLBAR_should_hide_toolbar() {
        waitForThread(Store.dispatch(Action(AppReducer.Actions.SET_LOGIN_PAGE_TOOLBAR)))
        val toolbar = onView(withId(R.id.toolbar))
        toolbar.check { view, noViewFoundException ->
            assertEquals(View.GONE, view.visibility)
        }
    }
}
