package cz.csas.weather.service

import cz.csas.weather.WeatherApplication
import cz.csas.weather.openapi.erste.placesapi.PagePlaces
import cz.csas.weather.openapi.erste.placesapi.Place
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.internal.verification.Times
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import java.io.IOException

@SpringBootTest(classes = [WeatherApplication::class])
@EnableAutoConfiguration
@ActiveProfiles("test")
class ErsteServiceTest {
    @MockBean
    @Qualifier("restTemplateForErsteAPI")
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var ersteService: ErsteService

    private val praguePlace = Place(
        type = "BRANCH",
        state = "OPEN",
        name = "Mostecká",
        address = "Mostecká 40/26",
        city = "Praha 1",
        country = "CZ"
    )

    private val praguePage = PagePlaces(
        pageNumber = 0,
        pageCount = 1,
        pageSize = 1,
        totalItemCount = 1,
        nextPage = null,
        items = listOf(praguePlace)
    )

    @BeforeEach
    fun before() {
        whenever(
            restTemplate.exchange(
                eq("/v3/places?radius&types=BRANCH&detail=MINIMAL&page=0&size=25&q=Prague"),
                eq(HttpMethod.GET),
                any(),
                any<Class<PagePlaces>>()
            )
        ).thenReturn(ResponseEntity(praguePage, HttpStatus.OK))

        whenever(
            restTemplate.exchange(
                eq("/v3/places?radius&types=BRANCH&detail=MINIMAL&page=0&size=25&q=Brno"),
                eq(HttpMethod.GET),
                any(),
                any<Class<PagePlaces>>()
            )
        ).thenReturn(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @Test
    fun getPlaces() {
        //city or coordinates must be filled
        assertThrows<IllegalArgumentException> {
            ersteService.getPlaces()
        }

        // normal flow
        val praguePageResponse = assertDoesNotThrow { ersteService.getPlaces("Prague") }
        assert(praguePageResponse == praguePage)

        //exception expected
        assertThrows<IOException> { ersteService.getPlaces("Brno") }

        verify(restTemplate, Times(2)).exchange(any<String>(), any(), any(), any<Class<PagePlaces>>())
    }
}
