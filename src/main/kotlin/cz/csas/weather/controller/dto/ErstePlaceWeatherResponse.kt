package cz.csas.weather.controller.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.hateoas.server.core.Relation

@Schema(
    title = "Erste Place Weather Response Body",
    description = "Returns details about Erste place and weather on place like type of place (BRANCH/ATM), " +
            "state of branch (OPEN/CLOSED/OUT OF ORDER), name of branch, address of place (street) and " +
            "city and country of place. Returns also current weather and its description and temperature in Kelvin."
)
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
