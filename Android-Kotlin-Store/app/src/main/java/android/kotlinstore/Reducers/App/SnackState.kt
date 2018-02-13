package android.kotlinstore.Reducers.App

import java.util.*

data class SnackState(
        var message: String = "Welcome",
        val isError: Boolean = false,
        val lastUpdate: Long = Date().time // force and update
)