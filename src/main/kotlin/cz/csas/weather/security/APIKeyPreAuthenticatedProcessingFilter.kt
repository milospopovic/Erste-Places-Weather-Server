package cz.csas.weather.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

/**
 * Filter for providing api key header.
 * @param apiKeyHeader name of header to retrieve
 */
class APIKeyPreAuthenticatedProcessingFilter(
    private val apiKeyHeader: String,
) : AbstractPreAuthenticatedProcessingFilter() {
    /**
     * Retrieves API key value from header {@link #apiKeyHeader}
     * @return value of header in String if provided, otherwise null
     */
    override fun getPreAuthenticatedPrincipal(httpServletRequest: HttpServletRequest): Any? =
        httpServletRequest.getHeader(apiKeyHeader)

    /**
     * No need to get credentials in this implementation,
     * @see AbstractPreAuthenticatedProcessingFilter#getPreAuthenticatedCredentials()
     */
    override fun getPreAuthenticatedCredentials(httpServletRequest: HttpServletRequest) = Any()
}
