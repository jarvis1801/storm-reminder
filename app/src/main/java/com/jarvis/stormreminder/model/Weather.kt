package com.jarvis.stormreminder.model

import java.io.Serializable

data class Weather(
    val generalSituation: String,
    val weatherForecast: MutableList<WeatherForecast>,
    val updateTime: String,
    val seaTemp: SeaTemp,
    val soilTemp: MutableList<SoilTemp>
)

data class WeatherForecast(
    val forecastDate: String,
    val week: String,
    val forecastWind: String,
    val forecastWeather: String,
    val forecastMaxtemp: Temp,
    val forecastMintemp: Temp,
    val forecastMaxrh: Temp,
    val forecastMinrh: Temp,
    val ForecastIcon: Double
)

data class SeaTemp(
    val place: String,
    val value: Double,
    val unit: String,
    val recordTime: String
)

data class SoilTemp(
    val place: String,
    val value: Double,
    val unit: String,
    val recordTime: String,
    val depth: Depth
)

data class Depth(
    val unit: String,
    val value: Double
)

data class Temp(
    val unit: String,
    val value: Double
)