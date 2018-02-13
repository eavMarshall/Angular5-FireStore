package android.kotlinstore.Views

import android.kotlinstore.R
import android.kotlinstore.getFullDate
import android.kotlinstore.getSelectedDate
import android.kotlinstore.getWeekDay
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_home_day.view.*
import java.util.*

class HomeDay : StoreFragment() {
    private var selectedDay : Date? = null

    companion object {
        private val ARG_SECTION_NUMBER = "section_number"
        private val ARG_STARTING_TIME = "starting_time"

        fun newInstance(sectionNumber: Int, startingTime: Long): HomeDay {
            val fragment = HomeDay()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            args.putLong(ARG_STARTING_TIME, startingTime)
            fragment.arguments = args
            return fragment
        }
    }

    private fun getTitleDate():String {
        return getWeekDay(selectedDay) + " " + getFullDate(selectedDay)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDay = getSelectedDate(arguments.getInt(ARG_SECTION_NUMBER))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.view_home_day, container, false)
        rootView.section_label.text = getTitleDate()

        return rootView
    }
}