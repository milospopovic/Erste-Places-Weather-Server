package cz.csas.weather.controller

import cz.csas.weather.WeatherApplication
import cz.csas.weather.model.ErsteData.praguePage
import cz.csas.weather.model.ErsteData.praguePlace
import cz.csas.weather.model.OpenWeatherData.pragueWeather
import cz.csas.weather.service.ErsteService
import cz.csas.weather.service.OpenWeatherService
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.IOException

@SpringBootTest(classes = [WeatherApplication::class])
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class ErstePlacesWeatherControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var ersteService: ErsteService

    @MockBean
    private lateinit var openWeatherService: OpenWeatherService

    private object Routes {
        fun getByCity(city: String) = MockMvcRequestBuilders.get("/places/weather/city/$city")
        fun getByCoordinates() = MockMvcRequestBuilders.get("/places/weather/coordinates")
    }

    @BeforeEach
    fun before() {
        whenever(
            ersteService.getPlaces("Prague")
        ).thenReturn(praguePage.copy(items = listOf(praguePlace.copy(city = "Prague"))))

        whenever(
            ersteService.getPlaces("Prague 1")
        ).thenReturn(praguePage)

        whenever(
            ersteService.getPlaces("Brno")
        ).thenAnswer { throw IOException() }

        whenever(
            openWeatherService.getCurrentWeather("Prague", "CZ")
        ).thenReturn(pragueWeather)

        whenever(
            openWeatherService.getCurrentWeather("Prague 1", "CZ")
        ).thenReturn(null)
    }

    @Test
    fun getByCity() {
        //no auth header
        mockMvc
            .perform(Routes.getByCity("Prague"))
            .andExpect(status().isForbidden)

        //normal flow with weather info provided
        mockMvc
            .perform(Routes.getByCity("Prague").header("X-API-KEY", "abc123"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[*]", Matchers.hasSize<Any>(praguePage.items.size)))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].type").value(praguePlace.type))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].state").value(praguePlace.state))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].address").value(praguePlace.address))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].city").value("Prague"))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].country").value(praguePlace.country))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].weather").value(pragueWeather.weather[0].main))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].weatherDescription").value(pragueWeather.weather[0].description))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].temperature").value(pragueWeather.main.temp))

        //normal flow without weather info provided
        mockMvc
            .perform(Routes.getByCity("Prague 1").header("X-API-KEY", "abc123"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[*]", Matchers.hasSize<Any>(praguePage.items.size)))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].weather").value(null))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].weatherDescription").value(null))
            .andExpect(jsonPath("$._embedded.ErstePlacesWeather.[0].temperature").value(null))

        //could not download este places
        mockMvc
            .perform(Routes.getByCity("Brno").header("X-API-KEY", "abc123"))
            .andExpect(status().isServiceUnavailable)

        //check wrong parameters validation
        mockMvc
            .perform(Routes.getByCity("Prague").header("X-API-KEY", "abc123").param("countryCode", "xxx"))
            .andExpect(status().isBadRequest)
        mockMvc
            .perform(Routes.getByCity("Prague").header("X-API-KEY", "abc123").param("page", "-1"))
            .andExpect(status().isBadRequest)
        mockMvc
            .perform(Routes.getByCity("Brno").header("X-API-KEY", "abc123").param("size", "0"))
            .andExpect(status().isBadRequest)
    }

    //TODO test for coordinates
}