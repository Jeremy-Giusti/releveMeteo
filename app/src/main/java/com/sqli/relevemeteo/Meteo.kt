package com.sqli.relevemeteo

import java.util.*

open class Meteo(var temperature: Int =10, var date: Date = Date(), var ensoleillement: Ensoleillement = Ensoleillement.NUAGEUX) {
    constructor(meteo: Meteo) : this(meteo.temperature, meteo.date, meteo.ensoleillement)
}

enum class Ensoleillement {
    SOLEIL,
    ORAGE,
    PLUIT,
    NUAGEUX
}

enum class MeteoFields{
    TEMPERATURE,
    DATE,
    ENSOLEILLEMENT
}