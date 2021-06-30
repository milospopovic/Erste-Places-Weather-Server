/**
 * File for providing Kotlin extension functions for rest of the code
 */
package cz.csas.weather.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.IOException

/**
 * Retrieves body if HTTP response returns status OK
 * @param T type of body
 * @return body of HTTP response if status is OK
 * @throws IOException if status of response is not OK or does not contain body
 */
fun <T> ResponseEntity<T>.getOrThrow(): T =
    if (this.statusCode == HttpStatus.OK && this.body != null)
        this.body!!
    else throw IOException("Unsuccessful HTTP request: $this")

/**
 * Function wraps object to {@link ResponseEntity} with HTTP status OK
 * @param Dto type of object to extend
 * @return ResponseEntity with {@link #this} object and status OK
 */
fun <Dto> Dto.ok(): ResponseEntity<Dto> {
    return ResponseEntity(this, HttpStatus.OK)
}
