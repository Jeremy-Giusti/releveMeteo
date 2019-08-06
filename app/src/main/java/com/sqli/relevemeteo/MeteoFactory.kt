package com.sqli.relevemeteo

import java.util.*
import kotlin.collections.ArrayList

/**
 * Singleton replace a database or a web client
 */
object MeteoFactory {
    private val meteoList = ArrayList<Meteo>().apply {
        add(Meteo(31, Date(), Ensoleillement.SOLEIL))
        add(Meteo(21, Date(1546764811000), Ensoleillement.PLUIT))
        add(Meteo(3, Date(1544086411000), Ensoleillement.SOLEIL))
        add(Meteo(14, Date(1583484811000), Ensoleillement.NUAGEUX))
        add(Meteo(13, Date(1559811211000), Ensoleillement.ORAGE))
        add(Meteo(19, Date(1568451211000), Ensoleillement.PLUIT))
        add(Meteo(0, Date(1558515211000), Ensoleillement.NUAGEUX))
    }

    fun addReleve(releveMeteo: ReleveMeteo) {
        meteoList.add(releveMeteo)
    }

    fun updateList(updatedList: List<Meteo>) {
        meteoList.replace(updatedList)
    }

    fun removeMeteoReleve(meteo : Meteo)
    {
        meteoList.remove(meteo)
    }

    fun getMeteoList() = meteoList

}
