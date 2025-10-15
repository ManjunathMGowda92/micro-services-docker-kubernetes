package org.fourstack.gatewayserver.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
public class FallbackController {

    @RequestMapping(value = "/contactSupport", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<String>> fallBackErrorResponse() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Mono.just("""
                        {
                        "message": "An error occurred due to some issue. Please retry after sometime or contact support team!",
                        "timestamp": "%s"
                        }
                        """.formatted(LocalDateTime.now().toString()))
                );
    }
}
