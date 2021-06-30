package cz.csas.weather.openapi.erste.placesapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PageItems(
    val pageNumber: Int,
    val pageCount: Int,
    val pageSize: Int,
    val nextPage: Int?,
    val totalItemCount: Int,
    val items: List<Item>,
)
