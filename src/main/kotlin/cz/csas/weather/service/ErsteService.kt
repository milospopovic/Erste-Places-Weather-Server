package cz.csas.weather.service

import cz.csas.weather.rest.erste.placesapi.PageItems
import cz.csas.weather.util.getOrThrow
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class ErsteService(
    @Qualifier("restTemplateForErsteAPI")
    private val restTemplate: RestTemplate,
) {
    private val log: Logger = LoggerFactory.getLogger(ErsteService::class.java)

    fun getPlaces(
        place: String? = null,
        country: String? = null,
        lat: Double? = null,
        lng: Double? = null,
        radius: Int? = null,
        types: String = "BRANCH",
        detail: String = "MINIMAL",
        page: Int = 0,
        pageSize: Int = 25,
    ): PageItems {
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

        val entity = HttpEntity<PageItems>(HttpHeaders())

        val response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, PageItems::class.java)
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
