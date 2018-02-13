package android.kotlinstore

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.util.*

fun hideKeyboard(context: Context) {
    try {
        val view = (context as Activity).window.currentFocus
        if (view != null && view.windowToken != null) {
            val binder = view.windowToken

            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binder, 0)
        }
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}

fun getLanguage():String {
    return Locale.getDefault().getDisplayLanguage()
}