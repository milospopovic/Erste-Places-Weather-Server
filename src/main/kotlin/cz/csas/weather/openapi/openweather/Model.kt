package cz.csas.weather.openapi.openweather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Data class for representation of current weather data
 * @see <a href="https://openweathermap.org/current">Current weather data API</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherData(
    /** City name */
    val name: String?,
    val weather: List<Weather>,
    val main: MainAttributes
)

data class Weather(
    /** Group of weather parameters (Rain, Snow, Extreme etc.) */
    val main: String?,
    /** Weather condition within the group. */
    val description: String?,
)

/**
 * All data is by default in Kelvin unit
 */
data class MainAttributes(
    /** Temperature */
    val temp: Double,
    /** This temperature parameter accounts for the human perception of weather. */
    val feels_like: Double,
    /**
     * Minimum temperature at the moment.
     * This is minimal currently observed temperature (within large megalopolises and urban areas).
     */
    val temp_min: Double,
    /**
     * Maximum temperature at the moment.
     * This is maximal currently observed temperature (within large megalopolises and urban areas).
     */
    val temp_max: Double,
    /** Atmospheric pressure */
    val pressure: Double,
    /** Humidity, % */
    val humidity: Double,
)
