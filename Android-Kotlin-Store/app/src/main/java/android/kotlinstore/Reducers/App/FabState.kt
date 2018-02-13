package android.kotlinstore.Reducers.App

data class FabState (
        var isOpen: Boolean = false,
        var icon: Int = 0,
        var showFab: Boolean = true
)