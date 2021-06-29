package cz.csas.weather.service.cache

import org.springframework.cache.CacheManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClearCacheTask(private val cacheManager: CacheManager) {
    /**
     * Clears cache for storing current weather at the beginning of every hour
     */
    @Scheduled(cron = "0 0 * * * *")
    fun clearCurrentWeatherCache(){
        cacheManager.getCache("currentWeather")?.clear()
    }
}
