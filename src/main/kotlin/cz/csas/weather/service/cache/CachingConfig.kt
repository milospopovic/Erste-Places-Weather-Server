package cz.csas.weather.service.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for cache management
 */
@Configuration
@EnableCaching
class CachingConfig {

    /**
     * Creates 2 caches for storing current weather and erste places
     */
    @Bean
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager(CURRENT_WEATHER_CACHE_NAME, ERSTE_PLACES_CACHE_NAME)
    }

    companion object {
        const val CURRENT_WEATHER_CACHE_NAME = "currentWeather"
        const val ERSTE_PLACES_CACHE_NAME = "erstePlaces"
    }
}
