package cz.csas.weather.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

/**
 * Filter for providing api key header.
 */
class APIKeyPreAuthenticatedProcessingFilter(
    private val apiKeyHeader: String,
) : AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(httpServletRequest: HttpServletRequest): Any? =
        httpServletRequest.getHeader(apiKeyHeader)

    override fun getPreAuthenticatedCredentials(httpServletRequest: HttpServletRequest) = Any()
}
