package com.sqli.relevemeteo

import java.util.*

open class Meteo(
    open val id: UUID = UUID.randomUUID(),
    open var temperature: Int = 10,
    open var date: Date = Date(),
    open var ensoleillement: Ensoleillement = Ensoleillement.NUAGEUX
) {
    constructor(meteo: Meteo) : this(meteo.id, meteo.temperature, meteo.date, meteo.ensoleillement)
}

enum class Ensoleillement {
    SOLEIL,
    BRUME,
    NUAGEUX,
    PLUIT,
    ORAGE
}

enum class MeteoField {
    TEMPERATURE,
    DATE,
    ENSOLEILLEMENT
}