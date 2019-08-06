package com.sqli.relevemeteo

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.releve_fragment.*


class ReleveDialog : DialogFragment(), AdapterView.OnItemSelectedListener {
    var releve: ReleveMeteo = ReleveMeteo()
    val ensoleillementList = Ensoleillement.values().apply { sortBy { it.toString() } }

    companion object {
        val TAG = ReleveDialog::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.releve_fragment, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initManualBinding()
        initSpinner()
        initConfirmButton()
    }

    private fun initSpinner() {
        releve_ensoleillement.onItemSelectedListener = this
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ensoleillementList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        releve_ensoleillement.adapter = adapter
    }

    private fun initConfirmButton() {
        validate_releve_button.setOnClickListener {
            validateReleve()
        }
    }

    private fun initManualBinding() {
        releve.run {
            releve_temperature.setText(temperature.asTemperatureString())
            releve_date.setText(dateDeReleve.asDisplayableString())
            meteo_date.setText(date.asDisplayableString())
        }

        releve_temperature.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toTemperatureInt()?.let { releve.temperature = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        releve_date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toDate()?.let { releve.dateDeReleve = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        meteo_date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toDate()?.let { releve.date = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        releve.ensoleillement = ensoleillementList[position]
    }

    private fun validateReleve() {
        Log.i(TAG, "releve créé : ${releve.toStringDisplayable()}")

        (activity as MainActivity).addMeteoReleve(releve)
        dismiss()
    }
}
