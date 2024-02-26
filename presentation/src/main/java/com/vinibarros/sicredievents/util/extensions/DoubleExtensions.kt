package com.vinibarros.sicredievents.util.extensions

import java.text.NumberFormat
import java.util.Locale


fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale("py", "br")).format(this)
}