package com.sqli.relevemeteo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqli.relevemeteo.MeteoFactory
import com.sqli.relevemeteo.ReleveDialog
import com.sqli.relevemeteo.ReleveMeteo

class ReleveEditionViewModel : ViewModel() {
    /**
     * the [ReleveMeteo] that we are creating/editing
     */
    private var releve: MutableLiveData<ReleveMeteo> = MutableLiveData<ReleveMeteo>().apply { value = ReleveMeteo() }

    fun getMutableReleveMeteo() = releve

    /**
     * add a [ReleveMeteo] into [meteoList] and refresh [displayedList]
     */
    fun saveReleve() {
        releve.value!!.let { savedReleve ->
            Log.i(ReleveDialog.TAG, "releve créé : ${savedReleve.toStringDisplayable()}")
            MeteoFactory.addOrUpdate(savedReleve)
        }
        releve.setValue(ReleveMeteo())
    }

    fun setReleve(releve: ReleveMeteo) {
        this.releve.value = releve
    }


}
