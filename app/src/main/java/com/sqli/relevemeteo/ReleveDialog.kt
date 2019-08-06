package com.sqli.relevemeteo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.releve_fragment.*

class ReleveDialog : DialogFragment() {

    var releve: ReleveMeteo = ReleveMeteo()

    companion object {
        val TAG = ReleveDialog::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.releve_fragment, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initManualBinding()
        initConfirmButton()
    }

    private fun initConfirmButton() {
        validate_releve_button.setOnClickListener {
            validateReleve()
        }
    }

    private fun validateReleve() {
        Toast.makeText(context, releve.toStringDisplayable(), Snackbar.LENGTH_LONG).show()
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
}
