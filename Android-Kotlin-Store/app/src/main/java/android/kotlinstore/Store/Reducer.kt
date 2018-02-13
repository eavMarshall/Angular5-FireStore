package android.kotlinstore.Store

abstract class Reducer {
    abstract fun reduce(storeState: Any?, action: Action):Any?
}