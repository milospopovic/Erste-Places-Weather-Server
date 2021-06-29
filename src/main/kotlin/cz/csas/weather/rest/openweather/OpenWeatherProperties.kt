package cz.csas.weather.rest.openweather

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration("openWeatherProperties")
@ConfigurationProperties(prefix = "openweather")
class OpenWeatherProperties {
    lateinit var url: String
    lateinit var apiKey: String
}
