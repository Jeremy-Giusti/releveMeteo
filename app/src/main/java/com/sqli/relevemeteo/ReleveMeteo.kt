package com.sqli.relevemeteo

import java.util.*

/**
 * Data class to store a meteo with a releve date
 */
data class ReleveMeteo(
    override var id: UUID,
    var dateDeReleve: Date = Date(),
    override var temperature: Int,
    override var date: Date,
    override var ensoleillement: Ensoleillement
) : Meteo(id, temperature, date, ensoleillement) {

    /**
     * Used to create a releve from a meteo
     */
    constructor(dateDeReleve: Date = Date(), meteo: Meteo = Meteo()) : this(
        meteo.id,
        dateDeReleve,
        meteo.temperature,
        meteo.date,
        meteo.ensoleillement
    )

    fun toStringDisplayable(): String {
        return "dateReleve ${dateDeReleve.asDisplayableString()} \n dateMeteo ${date.asDisplayableString()} \n temperature $temperature \n ensoleillement $ensoleillement"
    }
}