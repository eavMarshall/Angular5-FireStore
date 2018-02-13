package android.kotlinstore.Store

import java.util.logging.Logger

/**
 * Store
 *
 */
class Store { companion object {
    private var subscribers:HashMap<String, ArrayList<(old:Any?, new:Any?)->Unit>> = HashMap()
    private var fragmentSubscribers:HashMap<String, ArrayList<(old:Any?, new:Any?)->Unit>> = HashMap()
    private var reducers:HashMap<String, Reducer> = HashMap()
    var state:HashMap<String, Any?> = HashMap()
        private set

    fun reset() {
        synchronized(this) {
            state = HashMap()
            reducers.map {
                state.put(it.key, it.value.reduce(null, Action("")))
            }
        }
    }

    fun addReducer(storeName:String, reducer:Reducer) {
        if (!reducers.containsKey(storeName)) {
            reducers.put(storeName, reducer)
            state.put(storeName, reducer.reduce(null, Action("")))
        }
    }

    fun dispatch(action: Action):Thread {
        val t = Thread {
            Logger.getLogger("Store").warning("Action::${action.type}")
            for (r in reducers) {
                var oldState = state[r.key]
                var newState = r.value.reduce(state[r.key], action)
                if (newState != oldState) {
                    Logger.getLogger("Store").warning("old::$oldState")
                    Logger.getLogger("Store").warning("new::$newState")
                    state.put(r.key, newState)
                    changed(key = r.key, old = oldState, new = newState)
                }
            }
        }

        t.start()
        return t
    }

    fun dispatch(effect: Effect):Thread {
        val t = Thread {
            for (eff in effect.effect) {
                val t: Thread? = eff(state)
                if (null != t) {
                    while (t.isAlive) {
                        Thread.sleep(100)
                    }
                }
            }
        }

        t.start()
        return t
    }

    /**
     * Fragment level subscribers
     * This is a subscription that will be removed when the fragment is changed
     */
    fun fragmentSubscribe(reducerName:String, callBack:(old:Any?, new:Any?)->Unit) {
        synchronized(this) {
            if (null == fragmentSubscribers[reducerName]) {
                fragmentSubscribers.put(reducerName, ArrayList())
            }

            var sub = fragmentSubscribers[reducerName]
            if (sub is ArrayList) {
                sub.add(callBack)
            }
        }
    }

    /**
     * Activity level subscribers
     * This is a subscription for the life time of that app
     */
    fun subscribe(reducerName:String, callBack:(old:Any?, new:Any?)->Unit) {
        synchronized(this) {
            if (null == subscribers[reducerName]) {
                subscribers.put(reducerName, ArrayList())
            }

            var sub = subscribers[reducerName]
            if (sub is ArrayList) {
                sub.add(callBack)
            }
        }
    }

    fun clearFragmentSubscribers() {
        fragmentSubscribers = HashMap()
    }

    private fun changed(key:String, old: Any?, new: Any?) {
        synchronized(this) {
            val fabSubs = fragmentSubscribers[key]
            if (fabSubs is ArrayList) {
                for (sub in fabSubs) {
                    sub(old, new)
                }
            }

            val subs = subscribers.get(key)
            if (subs is ArrayList) {
                for (sub in subs) {
                    sub(old, new)
                }
            }
        }
    }
}}