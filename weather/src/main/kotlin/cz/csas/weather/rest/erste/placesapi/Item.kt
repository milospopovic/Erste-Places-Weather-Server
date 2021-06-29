package cz.csas.weather.rest.erste.placesapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(
    val type: String?,
    val state: String?,
    val name: String?,
    val address: String?,
    val city: String?,
    val country: String?,
)
