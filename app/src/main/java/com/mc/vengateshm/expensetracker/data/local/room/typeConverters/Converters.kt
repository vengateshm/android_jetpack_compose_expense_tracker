package com.mc.vengateshm.expensetracker.data.local.room.typeConverters

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun calendarToTimeInMillis(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun timeInMillisToCalendar(timeInMillis: Long): Calendar {
        return Calendar.getInstance().apply { this.timeInMillis = timeInMillis }
    }
}