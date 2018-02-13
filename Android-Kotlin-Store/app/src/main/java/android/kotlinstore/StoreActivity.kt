package android.kotlinstore

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.kotlinstore.Effects.AddBtnEffects
import android.kotlinstore.Effects.NavMenuBtnEffects
import android.kotlinstore.Reducers.AddToDay.AddToDayReducer
import android.kotlinstore.Reducers.App.*
import android.kotlinstore.Store.Store
import android.kotlinstore.Views.AddToDay
import android.kotlinstore.Views.HomeContainer
import android.kotlinstore.Views.Login
import android.kotlinstore.Views.Settings
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.net.URL
/**
 * I'm trying to keep all the activity related Store init and state update here
 */
abstract class StoreActivity: FireActivity() {
    private lateinit var toggle:ActionBarDrawerToggle
    protected fun setupStore() {
        Store.reset()
        Store.addReducer(AppReducer.name, AppReducer())
        Store.addReducer(AddToDayReducer.name, AddToDayReducer())

        Store.subscribe(reducerName = AppReducer.name, callBack = { old:Any?, new:Any? ->
            if (old is AppReducer.State && new is AppReducer.State) {
                if (old.dialog != new.dialog) updateDialogState(new.dialog)
                if (old.draw != new.draw) updateDrawState(new.draw)
                if (old.fab != new.fab) updateFabState(new.fab)
                if (old.snack != new.snack) updateSnackState(new.snack)
                if (old.toolbar != new.toolbar) updateToolBarState(new.toolbar)
                if (old.page != new.page) updatePageSelectState(new.page)
                if (old.user != new.user) { updateNavHeader(new.user) }
            }
        })

        Store.dispatch(AppReducer.SET_LOGIN_PAGE_ACTION())
    }

    private fun updateDialogState(state:DialogState) {
        hideKeyboard(this)
        runOnUiThread {
            AlertDialog.Builder(this).create().apply {
                setMessage(state.message)
                if (state.isError) {
                    // TODO make an error dialog?
                }
                if ("" != state.title) {
                    setTitle(state.title)
                }
                setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.yes), {
                    dialog, id ->
                    state.callBack()
                })
                setButton(AlertDialog.BUTTON_NEGATIVE, getString(android.R.string.no), {
                    dialog, id -> this.cancel()
                })
                show()
            }
        }
    }

    private fun updateSnackState(state: SnackState) {
        hideKeyboard(this)
        runOnUiThread {
            var snackBar = Snackbar.make(toolbar, state.message, Snackbar.LENGTH_LONG)
            if (state.isError) {
                snackBar.view.setBackgroundColor(getColor(R.color.colorError))
            }

            snackBar.show()
        }
    }

    private fun updateDrawState(state:DrawState) {
        if (state.isOpen) {
            runOnUiThread { drawer_layout.openDrawer(Gravity.LEFT) }
        } else {
            runOnUiThread { drawer_layout.closeDrawer(Gravity.LEFT) }
        }
    }

    private fun updateFabState(state:FabState) {
        Thread {
            runOnUiThread {
                fab.setImageDrawable(getDrawable(when(state.icon) {
                    1->R.mipmap.ic_save_white_24dp
                    else->R.mipmap.ic_add_white_24dp
                }))
            }

            val animSet = AnimationSet(true)
            animSet.interpolator = DecelerateInterpolator()
            animSet.fillAfter = true
            animSet.isFillEnabled = true


            var animRotate: RotateAnimation
            if (state.isOpen) {
                animRotate = RotateAnimation(
                        0f, -45f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                runOnUiThread {
                    fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorError))
                }
            } else {
                animRotate = RotateAnimation(
                        -45f, -0f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                runOnUiThread {
                    fab.backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorPrimary))
                }
            }

            if (state.showFab) {
                runOnUiThread { fab.show() }
            } else {
                runOnUiThread { fab.hide()}
                Store.dispatch(AppReducer.SET_DRAW_ACTION(DrawState(isOpen = false)))
            }

            animRotate.duration = 500
            animRotate.fillAfter = true
            animSet.addAnimation(animRotate)

            runOnUiThread { fab.startAnimation(animSet) }
        }.start()
    }

    private fun updateToolBarState(state:ToolbarState) {
        Thread {
            runOnUiThread { supportActionBar?.title = state.title }
            if (state.showToolbar) {
                runOnUiThread { supportActionBar?.show() }
            } else {
                runOnUiThread { supportActionBar?.hide() }
            }

            if (state.showBackBtn) {
                runOnUiThread { ObjectAnimator.ofFloat(toggle.drawerArrowDrawable, "progress", 1f).start() }
            } else {
                runOnUiThread { ObjectAnimator.ofFloat(toggle.drawerArrowDrawable, "progress", 0f).start() }
            }
        }.start()
    }

    private fun updatePageSelectState(state:PageState) {
        Thread {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            Store.clearFragmentSubscribers()
            fragmentTransaction.replace(fraglayout.id, when (state.selectedPage) {
                AppReducer.HOME_PAGE-> HomeContainer()
                AppReducer.SETTING_PAGE-> Settings()
                AppReducer.ADD_TO_DAY_PAGE-> AddToDay()
                else-> Login()
            })

            when (state.selectedPage) {
                AppReducer.HOME_PAGE->{ runOnUiThread { nav_view.getMenu().getItem(0).isChecked = true } }
                AppReducer.SETTING_PAGE->{ runOnUiThread {nav_view.getMenu().getItem(1).isChecked = true } }
                AppReducer.ADD_TO_DAY_PAGE->{ runOnUiThread {nav_view.getMenu().getItem(0).isChecked = true } }
            }

            fragmentTransaction.commitAllowingStateLoss()
            hideKeyboard(this)
            invalidateOptionsMenu()
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /*when (getState<PageReducer.State>(PageReducer.STORE_NAME).selectedPage) {
            PageReducer.HOME_PAGE->menuInflater.inflate(R.menu.home_menu, menu)
        }*/

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Store.dispatch(MenuEffects.instance.onMenuSelected(item.itemId))
        return true
    }


    private fun updateNavHeader(state: UserState) {
        Store.dispatch(when(state.uid) {""-> AppReducer.SET_LOGIN_PAGE_ACTION() else-> AppReducer.SET_HOME_PAGE_ACTION()})
        Thread {
            var bmp: Bitmap? = null
            var timeout = 0
            while (null == navDisplayName && timeout < 10) {
                Thread.sleep(100)
                timeout++
            }
            if (state.uid != "") {
                try {
                    val url = URL(state.photoUrl)
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                } catch (e: Exception) {
                }
            }
            runOnUiThread {
                navDisplayName.text = state.displayName
                navEmail.text = state.email
                if (null != bmp) {
                    nav_image.setImageBitmap(bmp)
                }
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view->Store.dispatch(AddBtnEffects.instance.click()) }

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationOnClickListener { view->
            Store.dispatch(NavMenuBtnEffects.instance.click())
        }
        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: android.view.View?, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: android.view.View?) {}
            override fun onDrawerClosed(drawerView: android.view.View?) {
                Store.dispatch(AppReducer.SET_DRAW_ACTION(DrawState(isOpen = false)))
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }
}