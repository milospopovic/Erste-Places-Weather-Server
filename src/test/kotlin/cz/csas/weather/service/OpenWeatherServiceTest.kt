package cz.csas.weather.service

import cz.csas.weather.WeatherApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate

@SpringBootTest(classes = [WeatherApplication::class])
@EnableAutoConfiguration
@ActiveProfiles("test")
class OpenWeatherServiceTest {
    @MockBean
    @Qualifier("restTemplateForOpenWeatherAPI")
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var openWeatherService: OpenWeatherService

    //TODO add tests like in ErsteServiceTest
}
