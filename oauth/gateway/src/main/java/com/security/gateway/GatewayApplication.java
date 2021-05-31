package com.security.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    //    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route(p ->
//                        p.path("/user-provider/**")
//                                .filters(f -> f.stripPrefix(1))
//                                .uri("lb://user-provider"))
                .route(p ->
                        p.path("/consumer/**")
                                .filters(f -> f.stripPrefix(1))
                                .uri("lb://user-consumer"))
                .build();
    }
}
