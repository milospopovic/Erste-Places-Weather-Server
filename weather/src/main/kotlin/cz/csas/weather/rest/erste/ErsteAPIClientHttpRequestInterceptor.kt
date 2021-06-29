package cz.csas.weather.rest.erste

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class ErsteAPIClientHttpRequestInterceptor(
    private val ersteProperties: ErsteProperties
) : ClientHttpRequestInterceptor {
    override fun intercept(
        httpRequest: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        httpRequest.headers.add(API_KEY_HEADER, ersteProperties.apiKey)
        return execution.execute(httpRequest, body)
    }

    companion object {
        private const val API_KEY_HEADER = "WEB-API-key"
    }
}
