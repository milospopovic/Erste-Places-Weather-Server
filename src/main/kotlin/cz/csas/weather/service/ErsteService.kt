package cz.csas.weather.service

import cz.csas.weather.openapi.erste.placesapi.PagePlaces
import org.springframework.stereotype.Service
import java.io.IOException

/**
 * Service for downloading data from erste api
 */
@Service
interface ErsteService {
    /**
     * Downloads data about erste places. Place or coordinates (@param lat, @param lng, @param radius) must be filled.
     * @param types type of place - BRANCH/ATM
     * @param detail Level of detail in returned objects
     *
     * @return page of places downloaded from erste api with information about pages
     * @throws IOException if request is not successful
     */
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
    ): PagePlaces
}
