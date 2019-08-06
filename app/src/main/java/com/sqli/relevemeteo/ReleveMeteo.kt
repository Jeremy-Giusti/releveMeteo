package com.sqli.relevemeteo

import java.util.*

data class ReleveMeteo(var dateDeReleve: Date = Date(), var meteo: Meteo = Meteo()) : Meteo(meteo) {
    fun toStringDisplayable(): String {
        return "dateReleve ${dateDeReleve.asDisplayableString()} \n dateMeteo ${date.asDisplayableString()} \n temperature $temperature \n ensoleillement $ensoleillement"
    }
}