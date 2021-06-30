package cz.csas.weather.openapi.erste.placesapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Data class represents single erste place
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class Place(
    /** BRANCH or ATM */
    val type: String?,
    /** OPEN / CLOSED / OUT OF ORDER */
    val state: String?,
    val name: String?,
    val address: String?,
    val city: String?,
    val country: String?,
)
