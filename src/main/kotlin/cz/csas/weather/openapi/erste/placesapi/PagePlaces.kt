package cz.csas.weather.openapi.erste.placesapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Data class represents page of @see Place.
 *
 * @see <a href="https://developers.erstegroup.com/docs/apis/bank.csas/bank.csas.v3%2Fplaces">Places API</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class PagePlaces(
    /** The page number of provided list items from total query result set. */
    val pageNumber: Int,
    /**
     * The total count of pages as result of calculation using
     * the requested page size and total records in query result set.
     */
    val pageCount: Int,
    val pageSize: Int,
    /**
     * Optional page number of the next page, if exists the nextPage value equals pageNumber+1,
     * if current pageNumber is the last page of result set then nextPage field is not provided.
     */
    val nextPage: Int?,
    val totalItemCount: Int,
    val items: List<Place>,
)
