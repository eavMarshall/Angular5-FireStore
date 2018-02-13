package android.kotlinstore.Reducers.App

import java.util.*

data class DialogState(
        var title: String = "",
        var message: String = "",
        var isError: Boolean = false,
        var callBack: () -> Unit = {},
        var date: Long = Date().time
)