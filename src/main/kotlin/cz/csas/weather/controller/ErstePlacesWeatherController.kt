package cz.csas.weather.controller

import cz.csas.weather.controller.dto.ErstePlaceWeatherResponse
import cz.csas.weather.openapi.erste.placesapi.PagePlaces
import cz.csas.weather.service.ErsteService
import cz.csas.weather.service.OpenWeatherService
import cz.csas.weather.util.ok
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controller for providing data about Erste places and current weather on places.
 */
@RestController
class ErstePlacesWeatherController(
    private val ersteService: ErsteService,
    private val openWeatherService: OpenWeatherService,
) : ErstePlacesWeatherOperations {
    /**
     * Provides weather on Erste places by city and country
     */
    override fun getErstePlacesWeatherByCity(
        city: String,
        countryCode: String?,
        page: Int,
        size: Int
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>> {
        require(countryCode == null || countryCode.length == COUNTRY_CODE_SIZE) {
            "country code must be empty or be exactly of size $COUNTRY_CODE_SIZE"
        }

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

    /**
     * Provides weather on Erste places by coordinates - latitude, longitude and radius
     */
    override fun getErstePlacesWeatherByCoordinates(
        lat: Double,
        lng: Double,
        radius: Int,
        page: Int,
        size: Int
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

    private fun PagePlaces.toResponse() = items.map {
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

        /** ISO 3166-1 alpha-2 */
        private const val COUNTRY_CODE_SIZE = 2
    }
}
