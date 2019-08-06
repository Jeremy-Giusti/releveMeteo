package com.sqli.relevemeteo

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sqli.relevemeteo.viewModel.ReleveEditionViewModel
import kotlinx.android.synthetic.main.releve_fragment.*


class ReleveDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    companion object {
        val TAG = ReleveDialog::class.java.name
    }


    lateinit var viewModel: ReleveEditionViewModel
    private var dismissListener: () -> Unit = {}

    /**
     * displayed list of [Ensoleillement]
     */
    val ensoleillementList = Ensoleillement.values().apply { sortBy { it.toString() } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.releve_fragment, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(ReleveEditionViewModel::class.java)
        bindEditTexts()
        initSpinner()
        initConfirmButton()
        observeReleve()

    }

    private fun observeReleve() {
        viewModel.getMutableReleveMeteo().observe(this, Observer<ReleveMeteo> {
            it.run {
                releve_temperature.setText(temperature.asTemperatureString())
                releve_date.setText(dateDeReleve.asDisplayableString())
                meteo_date.setText(date.asDisplayableString())
            }
        })
    }

    /**
     * [ReleveMeteo.ensoleillement] selection
     */
    private fun initSpinner() {
        releve_ensoleillement.onItemSelectedListener = this
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ensoleillementList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        releve_ensoleillement.adapter = adapter
    }

    /**
     * bind Edittexts for fields [ReleveMeteo.date], [ReleveMeteo.temperature], [ReleveMeteo.dateDeReleve]
     */
    private fun bindEditTexts() {

        val releve = viewModel.getMutableReleveMeteo().value

        releve_temperature.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toTemperatureInt()?.let { releve!!.temperature = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        releve_date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toDate()?.let { releve!!.dateDeReleve = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        meteo_date.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s.toString().toDate()?.let { releve!!.date = it }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    /**
     * on selection for [ReleveMeteo.ensoleillement]
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.getMutableReleveMeteo().value!!.ensoleillement = ensoleillementList[position]
    }

    private fun initConfirmButton() {
        validate_releve_button.setOnClickListener {
            validateReleve()
        }
    }

    /**
     * Save the edited [ReleveMeteo]
     */
    private fun validateReleve() {

        viewModel.saveReleve()
        dismiss()
    }

    fun setOnDismissListener(function: () -> Unit) {
        dismissListener = function
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        dismissListener()
    }
}
