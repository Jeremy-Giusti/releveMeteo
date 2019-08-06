package com.sqli.relevemeteo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.meteo_item.view.*

class MeteoAdapter(var meteoList: List<Meteo>, val itemClickCallback: (meteo: Meteo) -> Unit) :
    RecyclerView.Adapter<MeteoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meteo_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = meteoList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        meteoList[position].let { meteo -> holder.bind(meteo, itemClickCallback) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            meteo: Meteo,
            itemClickCallback: (meteo: Meteo) -> Unit
        ) {
            itemView.run {
                meteo_date.text = "${meteo.temperature}°C"
                meteo_temperature.text = "Date : ${meteo.date.asDisplayableString()}"
                meteo_ensoleillement.text = meteo.ensoleillement.toString()
                setOnLongClickListener{
                    itemClickCallback(meteo)
                    return@setOnLongClickListener true
                }
            }
        }
    }
}