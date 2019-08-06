package com.sqli.relevemeteo

import java.util.*
import kotlin.collections.ArrayList

fun getMeteoList(): List<Meteo> {
    return ArrayList<Meteo>().apply {
        add(Meteo(31, Date(), Ensoleillement.Soleil))
        add(Meteo(21, Date(), Ensoleillement.Pluit))
        add(Meteo(3, Date(), Ensoleillement.Soleil))
        add(Meteo(14, Date(), Ensoleillement.Nuageux))
        add(Meteo(13, Date(), Ensoleillement.Orage))
        add(Meteo(19, Date(), Ensoleillement.Pluit))
        add(Meteo(0, Date(), Ensoleillement.Nuageux))
    }
}