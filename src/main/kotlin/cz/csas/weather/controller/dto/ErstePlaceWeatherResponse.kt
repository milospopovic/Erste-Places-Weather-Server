package cz.csas.weather.controller.dto

import org.springframework.hateoas.server.core.Relation

@Relation(itemRelation = "ErstePlaceWeather", collectionRelation = "ErstePlacesWeather")
data class ErstePlaceWeatherResponse(
    val type: String?,
    val state: String?,
    val name: String?,
    val address: String?,
    val city: String?,
    val country: String?,
    val weather: String?,
    val weatherDescription: String?,
    val temperature: Double?
)
