package com.sqli.relevemeteo.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.sqli.relevemeteo.ReleveMeteo

class ReleveEditionViewModel : ViewModel() {
    /**
     * the [ReleveMeteo] that we are creating/editing
     */
    private var releve: MutableLiveData<ReleveMeteo> = MutableLiveData<ReleveMeteo>().apply { value = ReleveMeteo() }

     fun getMutableReleveMeteo() = releve




}
