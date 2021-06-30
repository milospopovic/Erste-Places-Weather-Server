package cz.csas.weather.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val securityProperties: SecurityProperties
) : WebSecurityConfigurerAdapter() {
    override fun configure(httpSecurity: HttpSecurity) {
        val filter = APIKeyPreAuthenticatedProcessingFilter(securityProperties.apiKeyHeader)
        filter.setAuthenticationManager(authenticationManager)

        httpSecurity
            .csrf().disable()
            .addFilter(filter)
            .authorizeRequests()
            .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
    }

    private val authenticationManager = AuthenticationManager { authentication ->
        val principal = authentication.principal as String
        if (principal != securityProperties.apiKeyValue)
            throw BadCredentialsException("Invalid API key")
        authentication.isAuthenticated = true
        authentication
    }
}
