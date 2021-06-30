package cz.csas.weather.controller

import cz.csas.weather.controller.dto.ErstePlaceWeatherResponse
import cz.csas.weather.openapi.erste.placesapi.PageItems
import cz.csas.weather.service.ErsteService
import cz.csas.weather.service.OpenWeatherService
import cz.csas.weather.util.ok
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller for providing data about Erste places and current weather on places.
 */
@RestController
@RequestMapping("/places/weather")
class ErstePlacesWeatherController(
    val ersteService: ErsteService,
    val openWeatherService: OpenWeatherService,
) {
    @Operation(summary = "Get weather on Erste places by city and country")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "Returned page of requested places with weather if provided",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = ErstePlaceWeatherResponse::class))
            )]
        ),
        ApiResponse(responseCode = "403", description = "Invalid api key", content = [Content()]),
    )
    @GetMapping("/city/{city}")
    fun getErstePlacesWeatherByCity(
        @PathVariable(name = "city", required = true) city: String,
        @RequestParam(name = "countryCode", required = false) countryCode: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "25") size: Int,
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>> {
        val ersteData = ersteService.getPlaces(
            place = city,
            country = countryCode,
            page = page,
            pageSize = size
        )

        val response = ersteData.toResponse()

        val links = mutableListOf<Link>()
        links.add(linkTo<ErstePlacesWeatherController> {
            getErstePlacesWeatherByCity(
                city,
                countryCode,
                ersteData.pageNumber,
                ersteData.pageSize
            )
        }.withSelfRel())
        if (ersteData.pageNumber > 0)
            links.add(linkTo<ErstePlacesWeatherController> {
                getErstePlacesWeatherByCity(
                    city,
                    countryCode,
                    ersteData.pageNumber - 1,
                    ersteData.pageSize
                )
            }.withRel(PAGE_PREV))
        if (ersteData.nextPage != null)
            links.add(linkTo<ErstePlacesWeatherController> {
                getErstePlacesWeatherByCity(
                    city,
                    countryCode,
                    ersteData.nextPage,
                    ersteData.pageSize
                )
            }.withRel(PAGE_NEXT))

        return CollectionModel.of(response, links).ok()
    }

    @Operation(summary = "Get weather on Erste places by coordinates - latitude, longitude and radius")
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "Returned page of requested places with weather if provided",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = ErstePlaceWeatherResponse::class))
            )]
        ),
        ApiResponse(responseCode = "403", description = "Invalid api key", content = [Content()]),
    )
    @GetMapping("/coordinates")
    fun getErstePlacesWeatherByCoordinates(
        @RequestParam(name = "lat", required = true) lat: Double,
        @RequestParam(name = "lng", required = true) lng: Double,
        @RequestParam(name = "radius", required = true) radius: Int,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "25") size: Int,
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>> {
        val ersteData = ersteService.getPlaces(
            lat = lat,
            lng = lng,
            radius = radius,
            page = page,
            pageSize = size
        )
        val response = ersteData.toResponse()

        val links = mutableListOf<Link>()
        links.add(linkTo<ErstePlacesWeatherController> {
            getErstePlacesWeatherByCoordinates(
                lat,
                lng,
                radius,
                ersteData.pageNumber,
                ersteData.pageSize
            )
        }.withSelfRel())
        if (ersteData.pageNumber > 0)
            links.add(linkTo<ErstePlacesWeatherController> {
                getErstePlacesWeatherByCoordinates(
                    lat,
                    lng,
                    radius,
                    ersteData.pageNumber - 1,
                    ersteData.pageSize
                )
            }.withRel(PAGE_PREV))
        if (ersteData.nextPage != null)
            links.add(linkTo<ErstePlacesWeatherController> {
                getErstePlacesWeatherByCoordinates(
                    lat,
                    lng,
                    radius,
                    ersteData.nextPage,
                    ersteData.pageSize
                )
            }.withRel(PAGE_NEXT))

        return CollectionModel.of(response, links).ok()
    }

    private fun PageItems.toResponse() = items.map {
        val city = it.city

        val weatherData = if (city != null)
            openWeatherService.getCurrentWeather(city = city, countryCode = it.country)
        else null

        ErstePlaceWeatherResponse(
            type = it.type,
            state = it.state,
            name = it.name,
            address = it.address,
            city = city,
            country = it.country,
            weather = weatherData?.weather?.firstOrNull()?.main,
            weatherDescription = weatherData?.weather?.firstOrNull()?.description,
            temperature = weatherData?.main?.temp
        )
    }

    companion object {
        private const val PAGE_PREV = "prev"
        private const val PAGE_NEXT = "next"
    }
}
