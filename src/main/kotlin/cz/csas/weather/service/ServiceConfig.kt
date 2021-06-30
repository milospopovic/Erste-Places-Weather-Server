package cz.csas.weather.service

import cz.csas.weather.openapi.openweather.OpenWeatherProperties
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * Configuration class for providing service beans
 */
@Configuration
class ServiceConfig {
    /**
     * Provides implementation of {@link ErsteService}
     */
    @Bean
    fun ersteService(
        @Qualifier("restTemplateForErsteAPI") restTemplate: RestTemplate,
    ): ErsteService = ErsteServiceImpl(restTemplate)

    /**
     * Provides implementation of {@link OpenWeatherService}
     */
    @Bean
    fun openWeatherService(
        @Qualifier("restTemplateForOpenWeatherAPI") restTemplate: RestTemplate,
        openWeatherProperties: OpenWeatherProperties,
    ): OpenWeatherService = OpenWeatherServiceImpl(restTemplate, openWeatherProperties)
}
