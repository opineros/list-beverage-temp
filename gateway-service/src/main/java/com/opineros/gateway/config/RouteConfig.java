package com.opineros.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, JwtAuthenticationFilter filter) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("http://localhost:8082"))
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter.apply(c -> {})))
                        .uri("http://localhost:8083"))
                .route("order-service", r -> r.path("/orders/**")
                        .filters(f -> f.filter(filter.apply(c -> {})))
                        .uri("http://localhost:8084"))
                .build();
    }
}
