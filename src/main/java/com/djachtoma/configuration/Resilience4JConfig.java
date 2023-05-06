package com.djachtoma.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> globalCircuitBreakerConfig() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig())
                .timeLimiterConfig(timeLimiterConfig())
                .build());
    }


    private CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(100)
                .failureRateThreshold(200)
                .waitDurationInOpenState(Duration.ofMillis(3000))
                .build();
    }

    private TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(10))
                .build();
    }
}
