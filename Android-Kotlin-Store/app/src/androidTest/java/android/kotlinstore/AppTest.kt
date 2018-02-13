package android.kotlinstore

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


/**
 *
 */
@RunWith(AndroidJUnit4::class)
class AppTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("android.kotlinstore", appContext.packageName)
    }
}
