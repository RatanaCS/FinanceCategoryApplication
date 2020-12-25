package com.sumuzu.financecategoryapplication.utils

import java.util.*

class CalendarUtil {
    companion object{
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
    }
}