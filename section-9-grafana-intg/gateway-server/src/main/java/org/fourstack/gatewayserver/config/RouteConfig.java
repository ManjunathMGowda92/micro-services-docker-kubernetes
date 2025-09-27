package org.fourstack.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.RouteMetadataUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class RouteConfig {

    @Autowired
    private KeyResolver keyResolver;

    @Autowired
    private RedisRateLimiter rateLimiter;

    @Bean
    public RouteLocator bankRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(apiPath -> apiPath.path("/bank-app/accounts/**")
                        .filters(api -> api.rewritePath("/bank-app/accounts/info", "/accounts-service/accounts/info")
                                .rewritePath("/bank-app/accounts/(?<segment>.*)", "/accounts-service/api/v1/accounts/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofMillis(2000), 2, true)
                                        .setMethods(HttpMethod.GET))
                        )
                        .metadata(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, 5000)
                        .metadata(RouteMetadataUtils.CONNECT_TIMEOUT_ATTR, 1000)
                        .uri("lb://ACCOUNTS"))
                .route(apiPath -> apiPath.path("/bank-app/customers/**")
                        .filters(api -> api.rewritePath("/bank-app/customers/(?<segment>.*)", "/accounts-service/api/v1/customers/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("customerCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofMillis(2000), 2, true)
                                        .setMethods(HttpMethod.GET)))
                        .metadata(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, 5000)
                        .metadata(RouteMetadataUtils.CONNECT_TIMEOUT_ATTR, 1000)
                        .uri("lb://ACCOUNTS"))
                .route(apiPath -> apiPath
                        .path("/bank-app/loans/**")
                        .filters(api -> api.rewritePath("/bank-app/loans/(?<segment>.*)", "/loans-service/api/v1/loans/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(4)
                                        .setBackoff(Duration.ofMillis(500), Duration.ofMillis(2000), 2, true)
                                        .setMethods(HttpMethod.GET))
                                .metadata("response-timeout", 5000)
                        )
                        .uri("lb://LOANS"))
                .route(apiPath -> apiPath.path("/bank-app/cards/**")
                        .filters(api -> api.rewritePath("/bank-app/cards/info", "/cards-service/cards/info")
                                .rewritePath("/bank-app/cards/(?<segment>.*)", "/cards-service/api/v1/cards/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .requestRateLimiter(config -> config.setKeyResolver(keyResolver)
                                        .setRateLimiter(rateLimiter)))
                        .uri("lb://CARDS"))
                .build();
    }
}
