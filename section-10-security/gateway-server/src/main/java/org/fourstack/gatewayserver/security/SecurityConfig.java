package org.fourstack.gatewayserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
         http.authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET).permitAll()
                                .pathMatchers("/bank-app/accounts/**").authenticated()
                                .pathMatchers("/bank-app/customers/**").authenticated()
                                .pathMatchers("/bank-app/loans/**").authenticated()
                                .pathMatchers("/bank-app/cards/**").authenticated())
                .oauth2ResourceServer(resourceServerSpec -> resourceServerSpec.jwt(Customizer.withDefaults()));

         http.csrf(ServerHttpSecurity.CsrfSpec::disable);
         return http.build();
    }
}
