package cz.csas.weather.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.IOException

fun <T> ResponseEntity<T>.getOrThrow(): T =
    if (this.statusCode == HttpStatus.OK && this.body != null)
        this.body!!
    else throw IOException("Unsuccessful HTTP request: $this")

fun <Dto> Dto.ok(): ResponseEntity<Dto> {
    return ResponseEntity(this, HttpStatus.OK)
}
