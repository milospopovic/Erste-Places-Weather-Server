package cz.csas.weather.rest.erste

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class Config(private val ersteProperties: ErsteProperties){
    @Bean("restTemplateForErsteAPI")
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
        return builder
            .rootUri(ersteProperties.url)
            .interceptors(ErsteAPIClientHttpRequestInterceptor(ersteProperties))
            .build()
    }
}
