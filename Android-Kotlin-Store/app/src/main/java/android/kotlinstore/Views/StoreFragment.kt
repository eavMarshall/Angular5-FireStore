package android.kotlinstore.Views

import android.kotlinstore.Store.Store
import android.support.v4.app.Fragment

/**
 * StoreFragment
 */
abstract class StoreFragment : Fragment() {
    override fun onDestroy() {
        super.onDestroy()
        Store.clearFragmentSubscribers()
    }
}