package org.fourstack.gatewayserver.config;

import org.springframework.cglib.core.Local;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator bankRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(apiPath -> apiPath
                        .path("/bank-app/accounts/**")
                        .filters(api -> api.rewritePath("/bank-app/accounts/info", "/accounts-service/accounts/info")
                                .rewritePath("/bank-app/accounts/(?<segment>.*)", "/accounts-service/api/v1/accounts/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .route(apiPath -> apiPath
                        .path("/bank-app/customers/**")
                        .filters(api -> api.rewritePath("/bank-app/customers/(?<segment>.*)", "/accounts-service/api/v1/customers/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("customerCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .route(apiPath -> apiPath
                        .path("/bank-app/loans/**")
                        .filters(api -> api.rewritePath("/bank-app/loans/(?<segment>.*)", "/loans-service/api/v1/loans/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(apiPath -> apiPath
                        .path("/bank-app/cards/**")
                        .filters(api -> api.rewritePath("/bank-app/cards/(?<segment>.*)", "/cards-service/api/v1/cards/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }
}
