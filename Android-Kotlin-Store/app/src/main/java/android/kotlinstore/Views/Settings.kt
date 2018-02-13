package android.kotlinstore.Views

import android.kotlinstore.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Settings : StoreFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.view_settings, container, false)
        return rootView
    }

    override fun onStart() {
        super.onStart()
    }
}