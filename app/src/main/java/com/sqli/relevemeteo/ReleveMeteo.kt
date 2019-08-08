package com.sqli.relevemeteo

import java.util.*

/**
 * class to store a meteo with a releve date
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

//TODO here we can find a few informations on how Meteo should be implemented