package android.kotlinstore.Reducers.App

data class UserState (
        val uid:String = "",
        val displayName: String = "Anonymous",
        val email: String = "",
        val photoUrl: String = ""
)