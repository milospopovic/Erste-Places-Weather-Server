package cz.csas.weather.controller

import cz.csas.weather.controller.dto.ErstePlaceWeatherResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.constraints.Max
import javax.validation.constraints.Min

/**
 * Operations for providing data about Erste places and current weather on places.
 */
@RequestMapping("/places/weather")
@Validated
interface ErstePlacesWeatherOperations {
    @Operation(
        summary = "Get weather on Erste places by city and country",
        description = "Returns page of Erste places by selected page and size of page"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "Returned page of requested places with weather if provided",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = ErstePlaceWeatherResponse::class))
            )]
        ),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Invalid api key", content = [Content()]),
        ApiResponse(responseCode = "503", description = "External API is not available", content = [Content()]),
    )
    @GetMapping("/city/{city}")
    fun getErstePlacesWeatherByCity(
        @PathVariable(name = "city", required = true) city: String,
        @RequestParam(name = "countryCode", required = false) countryCode: String?,
        @RequestParam(name = "page", required = false, defaultValue = "0") @Min(0) page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "25") @Min(1) size: Int,
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>>

    @Operation(
        summary = "Get weather on Erste places by coordinates - latitude, longitude and radius",
        description = "Returns page of Erste places by selected page and size of page"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200", description = "Returned page of requested places with weather if provided",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = ErstePlaceWeatherResponse::class))
            )]
        ),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
        ApiResponse(responseCode = "403", description = "Invalid api key", content = [Content()]),
        ApiResponse(responseCode = "503", description = "External API is not available", content = [Content()]),
    )
    @GetMapping("/coordinates")
    fun getErstePlacesWeatherByCoordinates(
        @RequestParam(name = "lat", required = true) @Min(-90) @Max(90) lat: Double,
        @RequestParam(name = "lng", required = true) @Min(-180) @Max(180) lng: Double,
        @RequestParam(name = "radius", required = true) @Min(0) radius: Int,
        @RequestParam(name = "page", required = false, defaultValue = "0") @Min(0) page: Int,
        @RequestParam(name = "size", required = false, defaultValue = "25") @Min(1) size: Int,
    ): ResponseEntity<CollectionModel<ErstePlaceWeatherResponse>>
}
