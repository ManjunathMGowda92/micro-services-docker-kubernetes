package org.fourstack.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
public class ResponseTraceFilter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    private FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
                chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    Optional<String> optionalCorrelationId = filterUtility.getCorrelationId(requestHeaders);
                    optionalCorrelationId.ifPresent(correlationId -> {
                        if (!exchange.getResponse().getHeaders().containsKey(FilterUtility.CORRELATION_ID)) {
                            logger.debug("Updated the correlationId to the outbound headers : {}", correlationId);
                            exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
                        }
                    });
                }));
    }
}
