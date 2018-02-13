package android.kotlinstore.Store

fun <T>getState(storeName:String):T {
    return Store.state.get(storeName) as T
}

fun waitForThread(t:Thread) {
    while (t.isAlive) {
        Thread.sleep(100)
    }
}