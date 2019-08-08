package com.sqli.relevemeteo

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


open class MainActivity : AppCompatActivity(){

    /**
     * list of meteo we want to display
     */
    private val meteoList = ArrayList<Meteo>().apply {
        //TODO Here we see how meteo should be instantiated
        add(Meteo(31, Date(), WeatherType.SOLEIL))
        add(Meteo(21, Date(1546764811000), WeatherType.PLUIT))
        add(Meteo(3, Date(1544086411000), WeatherType.SOLEIL))
        add(Meteo(14, Date(1583484811000), WeatherType.NUAGEUX))
        add(Meteo(13, Date(1559811211000), WeatherType.ORAGE))
        add(Meteo(19, Date(1568451211000), WeatherType.PLUIT))
        add(Meteo(0, Date(1558515211000), WeatherType.NUAGEUX))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayMeteoList()
    }

    /**
     * display the [displayedList] into [meteo_recycler_view]
     */
    private fun displayMeteoList() {
        meteo_recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)

        //create adapter for recycler view
        val adapter = MeteoAdapter(meteoList)
        //set recycler view adapter
        meteo_recycler_view.adapter = adapter
    }
}
