package com.sqli.relevemeteo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.meteo_item.view.*

class MeteoAdapter(
    var meteoList: List<Meteo>,
    val itemLongClickCallback: (meteo: Meteo) -> Unit,
    val itemClickCallback: (meteo: Meteo) -> Unit
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MeteoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.meteo_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = meteoList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        meteoList[position].let { meteo -> holder.bind(meteo, itemLongClickCallback, itemClickCallback) }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(
            meteo: Meteo,
            itemLongClickCallback: (meteo: Meteo) -> Unit,
            itemClickCallback: (meteo: Meteo) -> Unit
        ) {
            itemView.run {
                meteo_date.text = "${meteo.temperature}°C"
                meteo_temperature.text = "Date : ${meteo.date.asDisplayableString()}"
                meteo_ensoleillement.text = meteo.ensoleillement.toString()
                setOnLongClickListener {
                    itemLongClickCallback(meteo)
                    return@setOnLongClickListener true
                }
                setOnClickListener {
                    itemClickCallback(meteo)
                }
            }
        }
    }
}