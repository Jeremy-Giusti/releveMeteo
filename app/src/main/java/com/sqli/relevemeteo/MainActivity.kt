package com.sqli.relevemeteo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


open class MainActivity : AppCompatActivity() {

    private val meteoList = getMeteoList().apply { sortedBy { meteo -> meteo.date } }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMeteoList()
        initAddReleveButton()
    }

    private fun initMeteoList() {

        meteo_recycler_view.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MeteoAdapter(meteoList)
        }
    }

    private fun initAddReleveButton() {
        fab_add_meteo.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            ReleveDialog().show(ft, ReleveDialog.TAG)
        }
    }

    fun addMeteoReleve(releve : ReleveMeteo){
        meteoList.add(releve)
        meteo_recycler_view.adapter?.notifyDataSetChanged()
    }

}
