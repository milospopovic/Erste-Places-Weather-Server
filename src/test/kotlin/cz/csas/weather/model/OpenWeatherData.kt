package cz.csas.weather.model

import cz.csas.weather.openapi.openweather.MainAttributes
import cz.csas.weather.openapi.openweather.Weather
import cz.csas.weather.openapi.openweather.WeatherData

object OpenWeatherData {
    val pragueWeather = WeatherData(
        name = "Prague",
        weather = listOf(Weather(main = "Rain", description = "Raining cats and dogs")),
        main = MainAttributes(
            temp = 288.06,
            feels_like = 287.75,
            temp_min = 285.55,
            temp_max = 288.68,
            pressure = 1016.0,
            humidity = 82.0
        )
    )
}
