package com.sqli.relevemeteo

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqli.relevemeteo.viewModel.ListMeteoViewModel
import com.sqli.relevemeteo.viewModel.ReleveEditionViewModel
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var viewModel: ListMeteoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(ListMeteoViewModel::class.java)
        initMeteoList()
        initAddReleveButton()
        initSearchField()
        initSortSpinner()
    }

    /**
     * display the [displayedList] into [meteo_recycler_view]
     */
    private fun initMeteoList() {
        meteo_recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        viewModel.mutableMeteoList.observe(this, Observer<List<Meteo>> {
            meteo_recycler_view.adapter = MeteoAdapter(it, ::onMeteoLongClick,::onMeteoClick)
        })

    }

    /**
     * initialize button to create a new [ReleveMeteo] with [ReleveDialog]
     */
    private fun initAddReleveButton() {
        fab_add_meteo.setOnClickListener {
            showReleveEditionDialog()
        }
    }

    fun onMeteoClick(meteo: Meteo) {
        val releveViewModel = ViewModelProviders.of(this).get(ReleveEditionViewModel::class.java)
        releveViewModel.setReleve(ReleveMeteo(meteo = meteo))
        showReleveEditionDialog()
    }


        /**
     * Prompt a popup to confirm the removal of the selected [Meteo] from [meteo_recycler_view]
     */
    fun onMeteoLongClick(meteo: Meteo) {
        AlertDialog.Builder(this)
            .setMessage("Confirmer la suppression de la meteo")
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                removeMeteoReleve(meteo)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showReleveEditionDialog()
    {
        val ft = supportFragmentManager.beginTransaction()
        val dialog = ReleveDialog()
        dialog.setOnDismissListener {
            viewModel.refreshListMeteo()
        }
        dialog.show(ft, ReleveDialog.TAG)
    }

//region sort Spinner
    /**
     * Initialize the sort spinner with [Meteo] fields (which will be used to sort the displayable list)
     */
    private fun initSortSpinner() {
        meteo_sort_spinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, MeteoField.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        meteo_sort_spinner.adapter = adapter
    }

    /**
     * nothing to do
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    /**
     * we create a comparator matching the selected field and sort the [meteoList]
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val sortField = MeteoField.values()[position]
        viewModel.changeSortSubject(sortField)
    }
//endregion
//region search

    /**
     * Initialise the edit text used to filter [displayedList]
     */
    private fun initSearchField() {
        meteo_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterMeteo(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * remove all meteo whose attributs don't match the searched text from [displayedList]
     *
     * And then trigger the refresh of [meteo_recycler_view]
     */
    fun filterMeteo(filter: String) {
        viewModel.changeFilter(filter)
    }

//endregion


    /**
     * remove a [ReleveMeteo] into [meteoList] and refresh [displayedList]
     */
    fun removeMeteoReleve(meteo: Meteo) {
        viewModel.removeMeteoReleve(meteo)
    }

}
