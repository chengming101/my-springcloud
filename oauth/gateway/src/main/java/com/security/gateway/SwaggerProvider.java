package com.security.gateway;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";
    public static final String PREFIX = "provider";


    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;


    @Autowired
    public SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();
        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> StringUtils.contains(route.getUri().getHost(), PREFIX))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        Set<String> set = new HashSet<>();
        routeHosts.forEach(instance -> {
            String url = "/" + instance + API_URI;
            if (!set.contains(url)) {
                set.add(url);
                SwaggerResource swaggerResource = new SwaggerResource();
                swaggerResource.setUrl(url);
                swaggerResource.setName(instance);
                resources.add(swaggerResource);
            }
        });
        return resources;
    }
}