package cz.csas.weather.rest.erste

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration("ersteProperties")
@ConfigurationProperties(prefix = "erste")
class ErsteProperties {
    lateinit var url: String
    lateinit var apiKey: String
}
