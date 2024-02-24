package com.example.addAI.config

import com.example.addAI.utils.Logger
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled


@Configuration
@EnableCaching
@EnableScheduling
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager("rates")
    }

    @Scheduled(fixedRate = 86400000)
    @CacheEvict(value = ["rates"], allEntries = true)
    fun emptyHotelsCache() {
        Logger.logger.info("Evicting rates cache")
    }
}
