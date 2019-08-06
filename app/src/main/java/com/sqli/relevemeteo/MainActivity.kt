package com.sqli.relevemeteo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity() {

    private val meteoList = getMeteoList().sortMeteoListByDate().toMutableList()

    private val displayedList = meteoList.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMeteoList()
        initAddReleveButton()
        initSearchField()
    }

    private fun initSearchField() {
        meteo_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterMeteo(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initMeteoList() {

        meteo_recycler_view.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MeteoAdapter(displayedList, ::onMeteoClick)
        }
    }

    private fun initAddReleveButton() {
        fab_add_meteo.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ReleveDialog().show(ft, ReleveDialog.TAG)
        }
    }

    fun filterMeteo(filter: String) {
        fun doesFilterMatchMeteo(filter: String, meteo: Meteo): Boolean {
            return meteo.run {
                temperature == filter.toIntOrNull() || date.asDisplayableString() == filter || ensoleillement.toString() == filter
            }
        }

        if (filter.isNullOrBlank()) {
            displayedList.run {
                displayedList.clear()
                displayedList.addAll(meteoList)
            }
        } else {
            val filteredList = meteoList.filter {
                doesFilterMatchMeteo(filter, it)
            }
            displayedList.replace(filteredList)
        }
        meteo_recycler_view.adapter?.notifyDataSetChanged()
    }

    fun addMeteoReleve(releve: ReleveMeteo) {
        meteoList.add(releve)
        meteo_recycler_view.adapter?.notifyDataSetChanged()
    }

    fun removeMeteoReleve(meteo: Meteo) {
        meteoList.remove(meteo)
        meteo_recycler_view.adapter?.notifyDataSetChanged()
    }

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

}
