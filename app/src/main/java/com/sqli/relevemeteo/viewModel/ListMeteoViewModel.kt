package com.sqli.relevemeteo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.relevemeteo.*
import kotlin.text.toIntOrNull

class ListMeteoViewModel : ViewModel() {
    /**
     * store the full meteo list, used to add or remove items
     *
     * sorted by date by default
     */
    private var meteoFullList = MeteoFactory.getMeteoList().toList()

    var selectedMeteoFilter = ""

    var selectedMeteoSortSubject = MeteoField.DATE

    fun refreshListMeteo() {
        meteoFullList = MeteoFactory.getMeteoList().toList()
        updateMeteoList()
    }

    val mutableMeteoList = MutableLiveData<List<Meteo>>().apply { value = meteoFullList }

    fun changeFilter(filter: String) {
        selectedMeteoFilter = filter
        updateMeteoList()
    }

    fun changeSortSubject(enum: MeteoField) {
        selectedMeteoSortSubject = enum
        updateMeteoList()
    }

    private fun updateMeteoList() {
        //Filtrage
        var listMeteoTemp = if (selectedMeteoFilter.isBlank()) {
            //toMutable allow to update/modify the list
            meteoFullList.toMutableList()
        } else {
            meteoFullList.filter {
                doesFilterMatchMeteo(selectedMeteoFilter, it)
            }
        }

        //Trie
        val comparator = when (selectedMeteoSortSubject) {
            MeteoField.TEMPERATURE -> compareBy { it.temperature }
            MeteoField.DATE -> compareByDescending { it.date }
            MeteoField.ENSOLEILLEMENT -> compareBy<Meteo> { it.ensoleillement }
        }
        mutableMeteoList.value = listMeteoTemp.sortMeteoList(comparator)
    }

    private fun doesFilterMatchMeteo(filter: String, meteo: Meteo): Boolean {
        return meteo.run {
            temperature == filter.toIntOrNull() || date.asDisplayableString() == filter || ensoleillement.toString() == filter
        }
    }

    fun removeMeteoReleve(meteo : Meteo)
    {
        MeteoFactory.removeMeteoReleve(meteo)
        refreshListMeteo()
    }

}
