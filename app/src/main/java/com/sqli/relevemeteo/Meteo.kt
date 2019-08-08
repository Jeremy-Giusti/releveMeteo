package com.sqli.relevemeteo

import java.util.*

const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"

open class Meteo(
    open var temperature: Int = 10,
    open var date: Date = Date(),
    open var weatherType: WeatherType = WeatherType.NUAGEUX
) {
    constructor(meteo: Meteo) : this(meteo.temperature, meteo.date, meteo.weatherType)
}