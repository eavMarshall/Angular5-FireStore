package android.kotlinstore.Views

import android.kotlinstore.MainActivity
import android.kotlinstore.R
import android.kotlinstore.Reducers.App.AppReducer
import android.kotlinstore.Store.Store
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_login.*
import kotlinx.android.synthetic.main.view_login.view.*

class Login : StoreFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.view_login, container, false)
        rootView.LoginBtn.setOnClickListener {
            MainActivity.instance?.signIn()
        }
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Store.fragmentSubscribe(AppReducer.name, { old:Any?, new:Any? ->
            if (new is AppReducer.State) {
                if (activity is MainActivity && null != LoginBtn) {
                    activity.runOnUiThread({
                        if (new.isLoading) {
                            LoginBtn.visibility = View.INVISIBLE
                            LoginSpinner.visibility = View.VISIBLE
                        } else {
                            LoginBtn.visibility = View.VISIBLE
                            LoginSpinner.visibility = View.INVISIBLE
                        }
                    })
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }
}