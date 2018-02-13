package android.kotlinstore

import java.text.SimpleDateFormat
import java.util.*

fun removeTime(date: Date): Date {
    val cal = Calendar.getInstance()
    cal.time = date
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

fun getFullDate(d: Date?): String {
    if (null == d) return ""
    return SimpleDateFormat("dd MMMM yyyy")
            .format(d)
}

fun getWeekDay(d: Date?): String {
    if (null == d) return ""
    return SimpleDateFormat("EEEE")
            .format(d)
}

fun addDate(date:Date, days:Int): Date {
    val c = Calendar.getInstance()
    c.time = removeTime(date)
    c.add(Calendar.DATE, days)
    return removeTime(c.time)
}

fun getSelectedDate(selectedNum:Int):Date {
    val middle = Int.MAX_VALUE / 2
    return removeTime(addDate(Date(), selectedNum - middle))
}