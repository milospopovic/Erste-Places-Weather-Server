package cz.csas.weather.controller

import cz.csas.weather.security.SecurityProperties
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(title = "Erste Places Weather API", version = "v1"))
class OpenApi30Config {
    @Bean
    fun customOpenAPI(securityProperties: SecurityProperties): OpenAPI = OpenAPI()
        .addSecurityItem(SecurityRequirement().addList(securityProperties.apiKeyHeader))
        .components(
            Components()
            .addSecuritySchemes(
                securityProperties.apiKeyHeader,
                SecurityScheme()
                    .name(securityProperties.apiKeyHeader)
                    .`in`(SecurityScheme.In.HEADER)
                    .type(SecurityScheme.Type.APIKEY)
            )
        )
}
