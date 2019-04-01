package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfigurer {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Value("${app.angular}")
    private String frontend = "";

    @Value("${app.api}")
    private String api = "";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        if(frontend.isEmpty() || api.isEmpty()) {
            throw new IllegalStateException("front end and backed urls not configured in application.yml");
        }

        return builder.routes()
                .route("api", r -> r.path("/api/**")
                        .filters(f -> f.filter(filterFactory.apply()))
                        .uri(api))
                .route("angular", r -> r.path("/**")
                        .uri(frontend))
                .build();
    }
}
