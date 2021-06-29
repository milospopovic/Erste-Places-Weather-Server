package cz.csas.weather.service

import org.springframework.stereotype.Service

@Service
class DataService(
    val ersteService: ErsteService
) {
    fun getDataSet(
        place: String,
        country: String = "CZ",
        page: Int = 0,
        pageSize: Int = 25,
    ){
        val ersteData = ersteService.getPlaces(
            place = place,
            country = country,
            page = page,
            pageSize = pageSize
        )
    }
}
