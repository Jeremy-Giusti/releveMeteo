package com.sqli.relevemeteo

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

const val DEFAULT_DATE_FORMAT = "dd/MM/yyyy"

@DrawableRes
fun getDrawableForEnsoleillement(ensoleillement :Ensoleillement) : Int{
    return when(ensoleillement){
        Ensoleillement.SOLEIL -> R.drawable.weather_sunny
        Ensoleillement.ORAGE -> R.drawable.weather_lightning
        Ensoleillement.PLUIT -> R.drawable.weather_rainy
        Ensoleillement.NUAGEUX -> R.drawable.weather_cloudy
        Ensoleillement.BRUME -> R.drawable.weather_fog
    }
}

@ColorRes
fun getTintForEnsoleillement(ensoleillement :Ensoleillement) : Int{
    return when(ensoleillement){
        Ensoleillement.SOLEIL -> R.color.yellow_sunny
        Ensoleillement.ORAGE -> R.color.blue_orage
        Ensoleillement.PLUIT -> R.color.blue_rainy
        Ensoleillement.NUAGEUX -> R.color.white_cloud
        Ensoleillement.BRUME -> R.color.grey_fog
    }
}