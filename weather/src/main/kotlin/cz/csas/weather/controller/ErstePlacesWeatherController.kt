package cz.csas.weather.controller

import cz.csas.weather.controller.dto.ErstePlaceWeatherResponse
import cz.csas.weather.service.ErsteService
import cz.csas.weather.service.OpenWeatherService
import cz.csas.weather.util.ok
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/places/weather")
class ErstePlacesWeatherController(
    val ersteService: ErsteService,
    val openWeatherService: OpenWeatherService,
) {
    @GetMapping("/city/{city}")
    fun getErstePlacesWeatherByCity(
        @PathVariable(name = "city", required = true) city: String,
        @RequestParam(name = "countryCode", required = false) countryCode: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "25") size: Int,
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>> {
        val ersteData = ersteService.getPlaces(
            place = city,
            country = countryCode ?: "CZ",
            page = page,
            pageSize = size
        )

        val placesWeather = ersteData.items.map {
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
            }.withRel("prev"))
        if (ersteData.nextPage != null)
            links.add(linkTo<ErstePlacesWeatherController> {
                getErstePlacesWeatherByCity(
                    city,
                    countryCode,
                    ersteData.nextPage,
                    ersteData.pageSize
                )
            }.withRel("next"))

        return CollectionModel.of(placesWeather, links).ok()
    }
}
