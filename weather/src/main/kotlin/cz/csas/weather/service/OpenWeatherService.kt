package cz.csas.weather.service

import cz.csas.weather.rest.openweather.OpenWeatherProperties
import cz.csas.weather.rest.openweather.WeatherData
import cz.csas.weather.util.getOrThrow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Service
class OpenWeatherService(
    @Qualifier("restTemplateForOpenWeatherAPI")
    private val restTemplate: RestTemplate,
    private val openWeatherProperties: OpenWeatherProperties,
) {
    private val log: Logger = LoggerFactory.getLogger(OpenWeatherService::class.java)

    @Cacheable("currentWeather")
    fun getCurrentWeather(
        city: String,
        countryCode: String? = null
    ): WeatherData? {
        val place = city + if (countryCode != null) ",$countryCode" else ""

        val entity = HttpEntity<WeatherData>(HttpHeaders())

        val builder = UriComponentsBuilder.fromPath(PATH)
            .queryParam(PLACE, place)
            .addApiKeyParam()

        return kotlin.runCatching {
            val response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                WeatherData::class.java,
            )
            response.getOrThrow()
        }.onSuccess {
            log.info("Downloaded data from OpenWeather: $it")
        }.onFailure {
            log.info("Could not download weather for $place")
        }.getOrNull()
    }

    private fun UriComponentsBuilder.addApiKeyParam(): UriComponentsBuilder {
        queryParam(API_KEY_HEADER, openWeatherProperties.apiKey)
        return this
    }

    companion object {
        private const val PLACE = "q"
        private const val API_KEY_HEADER = "appid"
        private const val PATH = "/weather"

        private const val MAX_CALLS = 5
        private var calls = 0
    }
}
