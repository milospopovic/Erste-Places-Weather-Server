package cz.csas.weather.rest

import cz.csas.weather.rest.erste.ErsteAPIClientHttpRequestInterceptor
import cz.csas.weather.rest.erste.ErsteProperties
import cz.csas.weather.rest.openweather.OpenWeatherClientHttpRequestInterceptor
import cz.csas.weather.rest.openweather.OpenWeatherProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class Config(
    private val ersteProperties: ErsteProperties,
    private val openWeatherProperties: OpenWeatherProperties,
) {
    @Bean("restTemplateForErsteAPI")
    fun ersteRestTemplate(builder: RestTemplateBuilder): RestTemplate? {
        return builder
            .rootUri(ersteProperties.url)
            .interceptors(ErsteAPIClientHttpRequestInterceptor(ersteProperties))
            .build()
    }

    @Bean("restTemplateForOpenWeatherAPI")
    fun openWeatherRestTemplate(builder: RestTemplateBuilder): RestTemplate? {
        return builder
            .rootUri(openWeatherProperties.url)
            .interceptors(OpenWeatherClientHttpRequestInterceptor())
            .build()
    }
}
