package com.vinibarros.sicredievents.util.extensions

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

fun Long.getDate(): String {
    val calendar = Calendar.getInstance(Locale("pt", "BR"))
    calendar.timeInMillis = this
    return DateFormat.format("dd-MM-yyyy", calendar).toString()
}