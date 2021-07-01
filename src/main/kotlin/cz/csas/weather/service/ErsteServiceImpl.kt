package cz.csas.weather.service

import cz.csas.weather.openapi.erste.placesapi.PagePlaces
import cz.csas.weather.service.cache.CachingConfig.Companion.ERSTE_PLACES_CACHE_NAME
import cz.csas.weather.util.getOrThrow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation of service for downloading data from erste api
 */
class ErsteServiceImpl(
    private val restTemplate: RestTemplate,
) : ErsteService {
    private val log: Logger = LoggerFactory.getLogger(ErsteServiceImpl::class.java)

    /**
     * Downloads data about erste places. Place or coordinates (@param lat, @param lng, @param radius) must be filled.
     * @param types type of place - BRANCH/ATM
     * @param detail Level of detail in returned objects
     *
     * @return page of places downloaded from erste api with information about pages
     */
    @Cacheable(ERSTE_PLACES_CACHE_NAME)
    override fun getPlaces(
        place: String?,
        country: String?,
        lat: Double?,
        lng: Double?,
        radius: Int?,
        types: String,
        detail: String,
        page: Int,
        pageSize: Int,
    ): PagePlaces {
        require((place != null) || (lat != null && lng != null && radius != null)) {
            "city or coordinates must be filled"
        }

        val builder = UriComponentsBuilder.fromPath(PATH)
            .queryParam(PLACE, place)
            .queryParam(COUNTRY, country)
            .queryParam(LAT, lat)
            .queryParam(LNG, lng)
            .queryParam(RADIUS, radius)
            .queryParam(TYPES, types)
            .queryParam(DETAIL, detail)
            .queryParam(PAGE, page.toString())
            .queryParam(PAGE_SIZE, pageSize.toString())

        val entity = HttpEntity<PagePlaces>(HttpHeaders())

        val response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, PagePlaces::class.java)
        return response.getOrThrow().also {
            log.info("Downloaded data from erste: $it")
        }
    }

    companion object {
        private const val PATH = "/v3/places"

        private const val PLACE = "q"
        private const val COUNTRY = "country"
        private const val LAT = "lat"
        private const val LNG = "lng"
        private const val RADIUS = "radius"
        private const val TYPES = "types"
        private const val DETAIL = "detail"
        private const val PAGE = "page"
        private const val PAGE_SIZE = "size"
    }
}