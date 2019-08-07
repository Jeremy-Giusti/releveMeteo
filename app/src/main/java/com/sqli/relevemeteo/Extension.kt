package com.sqli.relevemeteo

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * concatenante "°C" at the end of an int
 */
fun Int.asTemperatureString() = "$this°C"

/**
 * use [DEFAULT_DATE_FORMAT] to transform a [Date] to a string
 */
fun Date.asDisplayableString() = android.text.format.DateFormat.format(DEFAULT_DATE_FORMAT, this).toString()


/**
 * remove the last 2 characters and try to create an Int with the result
 *
 *
 * see [Int.asTemperatureString] for the "reverse"
 */
fun String.toTemperatureInt(): Int? {
    return try {
        this.substring(IntRange(0, length - 3)).toInt()
    } catch (error: NumberFormatException) {
        null
    }
}

/**
 * reverse of [Date.asDisplayableString]
 *
 * use [DEFAULT_DATE_FORMAT] to format
 */
fun String.toDate(): Date? {
    return try {
        val format = SimpleDateFormat(DEFAULT_DATE_FORMAT)
        format.parse(this)
    } catch (error: ParseException) {
        null
    }
}

/**
 * clean the List and addAll item passed as parameter
 */
fun <T> MutableList<T>.replace(list: List<T>) {
    this.clear()
    this.addAll(list)
}

/**
 * same as [String.toInt] but with [NumberFormatException] handling
 */
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

/**
 * @param comparator comparator used to sort the list
 * @return the list subject of this sort
 */
fun List<Meteo>.sortMeteoList(comparator: Comparator<Meteo>): List<Meteo> {
    return sortedWith(comparator)
}

/**
 * Replace a value by [newValue]
 *
 * Chronologicaly does :
 * 1. find the item from the list that match the lambda condition
 * 2. add [newValue] next to the item found
 * 3. remove the item found
 *
 * @param newValue the value that will replace the first value that match [findLambda]
 * @param findLambda lambda used to find the value to replace
 * @return true if an item has been replaced, false otherwise
 */
fun <T> MutableList<T>.replace(newValue: T, findLambda: (T) -> Boolean) : Boolean {
    find { findLambda(it) }?.let {
        val index = indexOf(it)
        set(index, newValue)
        return true
    }
    return false
}