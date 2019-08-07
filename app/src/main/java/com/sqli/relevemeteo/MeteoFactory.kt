package com.sqli.relevemeteo

import java.util.*
import kotlin.collections.ArrayList

/**
 * Singleton replace a database or a web client
 */
object MeteoFactory {
    private val meteoList = ArrayList<Meteo>().apply {
        add(Meteo(UUID.randomUUID(),31, Date(), Ensoleillement.SOLEIL))
        add(Meteo(UUID.randomUUID(),21, Date(1546764811000), Ensoleillement.PLUIT))
        add(Meteo(UUID.randomUUID(),3, Date(1544086411000), Ensoleillement.SOLEIL))
        add(Meteo(UUID.randomUUID(),14, Date(1583484811000), Ensoleillement.NUAGEUX))
        add(Meteo(UUID.randomUUID(),13, Date(1559811211000), Ensoleillement.ORAGE))
        add(Meteo(UUID.randomUUID(),19, Date(1568451211000), Ensoleillement.PLUIT))
        add(Meteo(UUID.randomUUID(),0, Date(1558515211000), Ensoleillement.NUAGEUX))
    }

    fun addOrUpdate(releveMeteo: ReleveMeteo) {
        val hasReplacedMeteo = meteoList.replace(releveMeteo){meteo -> meteo.id == releveMeteo.id }

        if(!hasReplacedMeteo){
            meteoList.add(releveMeteo)
        }
    }

    fun removeMeteoReleve(meteo : Meteo)
    {
        meteoList.remove(meteo)
    }

    fun getMeteoList() = meteoList.toMutableList()

}
