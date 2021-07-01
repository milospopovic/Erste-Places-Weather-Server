package cz.csas.weather.model

import cz.csas.weather.openapi.erste.placesapi.PagePlaces
import cz.csas.weather.openapi.erste.placesapi.Place

object ErsteData {
    val praguePlace = Place(
        type = "BRANCH",
        state = "OPEN",
        name = "Mostecká",
        address = "Mostecká 40/26",
        city = "Prague 1",
        country = "CZ"
    )

    val praguePage = PagePlaces(
        pageNumber = 0,
        pageCount = 1,
        pageSize = 1,
        totalItemCount = 1,
        nextPage = null,
        items = listOf(praguePlace)
    )
}
