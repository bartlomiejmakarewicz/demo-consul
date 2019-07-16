package com.example.democonsul

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("distributions-api/distribution-stats")
interface StatsClient {
    @GetMapping("/healthcheck")
    fun statsHealth(): Any
}

@Component
class StatsHealth(val statsClient: StatsClient): HealthIndicator {
    override fun health(): Health {
        try {
            statsClient.statsHealth()
        } catch (e: Exception) {
            return Health.down().build()
        }
        return Health.up().build()
    }
}
