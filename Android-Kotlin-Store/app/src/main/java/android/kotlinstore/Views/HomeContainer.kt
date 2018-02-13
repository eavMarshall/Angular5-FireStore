package android.kotlinstore.Views

import android.kotlinstore.R
import android.kotlinstore.removeTime
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_home_container.*
import kotlinx.android.synthetic.main.view_home_container.view.*
import java.util.*

class HomeContainer : StoreFragment() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.view_home_container, container, false)
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        rootView.scrollingView.adapter = mSectionsPagerAdapter
        return rootView
    }
    override fun onStart() {
        super.onStart()
        scrollingView.currentItem = Int.MAX_VALUE / 2
    }

    class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val startingDate = removeTime(Date())
        override fun getItem(position: Int): Fragment {
            return HomeDay.newInstance(position, startingDate.time)
        }

        override fun getCount(): Int {
            return Int.MAX_VALUE
        }
    }
}