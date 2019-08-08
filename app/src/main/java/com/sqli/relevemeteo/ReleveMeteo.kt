package com.sqli.relevemeteo

import java.util.*

/**
 * Data class to store a meteo with a releve date
 */
class ReleveMeteo(
    var dateDeReleve: Date = Date(),
    temperature: Int,
    date: Date,
    weatherType: WeatherType
) : Meteo(
    temperature,
    date,
    weatherType
)