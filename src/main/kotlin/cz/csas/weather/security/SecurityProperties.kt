package cz.csas.weather.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration("securityProperties")
@ConfigurationProperties(prefix = "security")
class SecurityProperties {
    lateinit var apiKeyHeader: String
    lateinit var apiKeyValue: String
}
