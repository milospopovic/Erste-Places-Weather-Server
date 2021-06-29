package cz.csas.weather.service

import org.springframework.stereotype.Service

@Service
class DataService(
    val ersteService: ErsteService,
    val openWeatherService: OpenWeatherService,
) {
    fun getDataSet(
        place: String,
        countryCode: String = "CZ",
        page: Int = 0,
        pageSize: Int = 25,
    ){
        val ersteData = ersteService.getPlaces(
            place = place,
            country = countryCode,
            page = page,
            pageSize = pageSize
        )

        ersteData.items.forEach {
            openWeatherService.getCurrentWeather(city = it.city ?: return@forEach, countryCode = it.country)
        }
    }
}
