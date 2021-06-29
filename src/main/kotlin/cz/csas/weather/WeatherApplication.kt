package cz.csas.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WeatherApplication

fun main(args: Array<String>) {
	runApplication<WeatherApplication>(*args)
}
