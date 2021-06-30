package cz.csas.weather.service

import cz.csas.weather.openapi.openweather.WeatherData
import org.springframework.stereotype.Service

/**
 * Service for downloading data from openweather api
 */
@Service
interface OpenWeatherService {
    /**
     * Downloads data about current weather by @param city and optionally @param countryCode
     * @return basic information about current weather for @param city or null if not provided
     */
    fun getCurrentWeather(city: String, countryCode: String? = null): WeatherData?
}
