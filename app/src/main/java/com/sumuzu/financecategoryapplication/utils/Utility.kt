package com.sumuzu.financecategoryapplication.utils

import java.text.DecimalFormat
import java.text.NumberFormat

fun idFormat(number : Int) : String{
    val decimalFormat : NumberFormat = DecimalFormat("#,###")
    return decimalFormat.format(number)
}