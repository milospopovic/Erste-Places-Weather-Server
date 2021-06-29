package cz.csas.weather.rest.openweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class Weather(
    val main: String?,
    val description: String?,
)

data class MainAttributes(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherData(
    val name: String?,
    val weather: List<Weather>,
    val main: MainAttributes
)
