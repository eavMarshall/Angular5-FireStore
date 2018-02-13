package android.kotlinstore.Views

import android.kotlinstore.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AddToDay : StoreFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.view_add_to_day, container, false)
        return rootView
    }

    override fun onStart() {
        super.onStart()
    }
}
