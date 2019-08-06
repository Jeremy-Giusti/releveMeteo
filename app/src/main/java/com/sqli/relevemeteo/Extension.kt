package com.sqli.relevemeteo

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Int.asTemperatureString() = "$this°C"

fun Date.asDisplayableString() = android.text.format.DateFormat.format(DEFAULT_DATE_FORMAT, this)


fun String.toTemperatureInt(): Int? {
    return try {
        this.substring(IntRange(0, length - 3)).toInt()
    } catch (error: NumberFormatException) {
        null
    }
}

fun String.toDate(): Date? {
    return try {
        val format = SimpleDateFormat(DEFAULT_DATE_FORMAT)
        format.parse(this)
    } catch (error: ParseException) {
        null
    }
}

fun <T> MutableList<T>.replace(list: List<T>) {
    this.clear()
    this.addAll(list)
}

fun String.toIntOrNull(): Int? {
    return try {
        toInt()
    } catch (error: NumberFormatException) {
        null
    }
}


fun List<Meteo>.sortMeteoListByDate(): List<Meteo> {
    return sortedWith(compareByDescending { it.date })
}
