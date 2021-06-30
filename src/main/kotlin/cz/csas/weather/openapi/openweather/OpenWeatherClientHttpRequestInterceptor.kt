package cz.csas.weather.openapi.openweather

import org.springframework.http.HttpRequest
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class OpenWeatherClientHttpRequestInterceptor : ClientHttpRequestInterceptor {
    override fun intercept(
        httpRequest: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = httpRequest.headers
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        return execution.execute(httpRequest, body)
    }
}
