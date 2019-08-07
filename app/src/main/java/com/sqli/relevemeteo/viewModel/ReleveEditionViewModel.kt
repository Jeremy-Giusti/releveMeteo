package com.sqli.relevemeteo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.relevemeteo.*
import java.util.*

class ReleveEditionViewModel : ViewModel() {

    var releveDateLiveData: MutableLiveData<String> = MutableLiveData<String>()
        .apply { value = Date().asDisplayableString() }

    var temperatureLiveData: MutableLiveData<String> = MutableLiveData<String>()
        .apply { value = "15°C" }

    var meteoDateLiveData: MutableLiveData<String> = MutableLiveData<String>()
        .apply { value = Date().asDisplayableString() }

    //TODO databinding (with a spinner)
    var ensoleillement: Ensoleillement = Ensoleillement.SOLEIL

    private var releveId: UUID? = null

    fun setReleve(releve: ReleveMeteo) {
        releveDateLiveData.value = releve.dateDeReleve.asDisplayableString()
        meteoDateLiveData.value = releve.date.asDisplayableString()
        temperatureLiveData.value = releve.temperature.asTemperatureString()
        ensoleillement = releve.ensoleillement
        releveId = releve.id
    }

    /**
     * add a [ReleveMeteo] into [meteoList] and refresh [displayedList]
     */
    fun saveReleve() {
        val releveMeteo = createReleveFromFields()
        MeteoFactory.addOrUpdate(releveMeteo)
        Log.i(ReleveDialog.TAG, "relevé créé : ${releveMeteo.toStringDisplayable()}")
        resetDatas()
    }

    private fun resetDatas() {
        releveId = null
        ensoleillement = Ensoleillement.SOLEIL
        releveDateLiveData.value = Date().asDisplayableString()
        meteoDateLiveData.value = Date().asDisplayableString()
        temperatureLiveData.value = "15C°"
    }

    /**
     * create a releveMeteo using fields provided by this viewModel
     */
    private fun createReleveFromFields(): ReleveMeteo {
        val releveMeteo = ReleveMeteo()

        val releveDate = releveDateLiveData.value!!.toDate()
        val temperature = temperatureLiveData.value!!.toTemperatureInt()
        val meteoDate = meteoDateLiveData.value!!.toDate()

        if (releveId != null) releveMeteo.id = releveId!!
        if (releveDate != null) releveMeteo.dateDeReleve = releveDate
        if (temperature != null) releveMeteo.temperature = temperature
        if (meteoDate != null) releveMeteo.date = meteoDate
        releveMeteo.ensoleillement = ensoleillement

        return releveMeteo
    }

}
