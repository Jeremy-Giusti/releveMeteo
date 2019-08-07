package com.sqli.relevemeteo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sqli.relevemeteo.databinding.ReleveFragmentBinding
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
    val ensoleillementList = Ensoleillement.values()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //databinding magic
        val binding: ReleveFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.releve_fragment,
            container,
            true
        )
        viewModel = ViewModelProviders.of(activity!!).get(ReleveEditionViewModel::class.java)
        binding.releveViewModel = viewModel
        /** allow livedata binding */
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initConfirmButton()
    }

    /**
     * [ReleveMeteo.ensoleillement] selection
     */
    private fun initSpinner() {
        releve_ensoleillement.onItemSelectedListener = this
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ensoleillementList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        releve_ensoleillement.adapter = adapter
        releve_ensoleillement.setSelection(viewModel.ensoleillement.ordinal)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    /**
     * on selection for [ReleveMeteo.ensoleillement]
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.ensoleillement = ensoleillementList[position]
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
