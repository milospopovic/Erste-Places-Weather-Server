package cz.csas.weather.service.cache

import cz.csas.weather.service.cache.CachingConfig.Companion.CURRENT_WEATHER_CACHE_NAME
import cz.csas.weather.service.cache.CachingConfig.Companion.ERSTE_PLACES_CACHE_NAME
import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Class for providing tasks to clear caches periodically
 */
@Component
class ClearCacheTask(private val cacheManager: CacheManager) {
    /**
     * Clears cache for storing current weather at the beginning of every hour
     */
    @Scheduled(cron = "0 0 * * * *")
    fun clearCurrentWeatherCache(){
        cacheManager.getCache(CURRENT_WEATHER_CACHE_NAME)?.clear()
    }
    /**
     * Clears cache for storing erste places every day
     */
    @Scheduled(cron = "0 0 0 * * *")
    fun clearErstePlacesCache(){
        cacheManager.getCache(ERSTE_PLACES_CACHE_NAME)?.clear()
    }
}
