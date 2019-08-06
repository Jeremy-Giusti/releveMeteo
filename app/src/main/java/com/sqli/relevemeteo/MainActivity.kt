package com.sqli.relevemeteo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    /**
     * store the full meteo list, used to add or remove items
     *
     * sorted by date by default
     */
    private val meteoList = getMeteoList().sortMeteoListByDate().toMutableList()

    /**
     * hold the displayed meteo list (can be sorted / filtered)
     */
    private val displayedList = meteoList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMeteoList()
        initAddReleveButton()
        initSearchField()
        initSortSpinner()
    }

    /**
     * display the [displayedList] into [meteo_recycler_view]
     */
    private fun initMeteoList() {

        meteo_recycler_view.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MeteoAdapter(displayedList, ::onMeteoClick)
        }
    }

    /**
     * Prompt a popup to confirm the removal of the selected [Meteo] from [meteo_recycler_view]
     */
    fun onMeteoClick(meteo: Meteo) {
        android.app.AlertDialog.Builder(this)
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

    /**
     * initialize button to create a new [ReleveMeteo] with [ReleveDialog]
     */
    private fun initAddReleveButton() {
        fab_add_meteo.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ReleveDialog().show(ft, ReleveDialog.TAG)
        }
    }

    //region sort Spinner
    /**
     * Initialize the sort spinner with [Meteo] fields (which will be used to sort the displayable list)
     */
    private fun initSortSpinner() {
        meteo_sort_spinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, MeteoFields.values())
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
        val sortField = MeteoFields.values()[position]
        val comparator = when (sortField) {
            MeteoFields.TEMPERATURE -> compareBy { it.temperature }
            MeteoFields.DATE -> compareByDescending { it.date }
            MeteoFields.ENSOLEILLEMENT -> compareBy<Meteo> { it.ensoleillement }
        }
        displayedList.replace(displayedList.sortMeteoList(comparator))
        meteo_recycler_view.adapter?.notifyDataSetChanged()
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
        fun doesFilterMatchMeteo(filter: String, meteo: Meteo): Boolean {
            return meteo.run {
                temperature == filter.toIntOrNull() || date.asDisplayableString() == filter || ensoleillement.toString() == filter
            }
        }

        if (filter.isBlank()) {
            displayedList.replace(meteoList)
        } else {
            val filteredList = meteoList.filter {
                doesFilterMatchMeteo(filter, it)
            }
            displayedList.replace(filteredList)
        }
        meteo_recycler_view.adapter?.notifyDataSetChanged()
    }

    //endregion

    /**
     * add a [ReleveMeteo] into [meteoList] and refresh [displayedList]
     */
    fun addMeteoReleve(releve: ReleveMeteo) {
        meteoList.add(releve)
        //since we don't use View Model we have to handle those kind of event manually
        // here we reset the search which will trigger a display of the meteoList
        meteo_search.setText("")
    }

    /**
     * remove a [ReleveMeteo] into [meteoList] and refresh [displayedList]
     */
    fun removeMeteoReleve(meteo: Meteo) {
        meteoList.remove(meteo)
        //since we don't use View Model we have to handle those kind of event manually
        // here we reset the search which will trigger a display of the meteoList
        meteo_search.setText("")
    }

}
