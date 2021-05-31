package com.security.commons;

import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.redis.request.RedisClusterRateLimiterFactory;
import es.moki.ratelimitj.redis.request.RedisRateLimiterFactory;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Configuration
public class RedisRateLimit {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    RequestRateLimiter requestRateLimiter() {
        if (!"localhost".equals(redisProperties.getHost())) {
            Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(Duration.ofSeconds(10), 10));
//        RedisURI uri = RedisURI.create(redisProperties.getHost(), redisProperties.getPort());;
            RedisURI uri = RedisURI.builder().withHost(redisProperties.getHost()).withPort(redisProperties.getPort()).withPassword(redisProperties.getPassword()).build();
            RedisClient redisClient = RedisClient.create(uri);

            RedisRateLimiterFactory redisRateLimiterFactory = new RedisRateLimiterFactory(redisClient);
            return redisRateLimiterFactory.getInstance(rules);
        } else {
            Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(Duration.ofSeconds(10), 10));
            List<String> nodes = redisProperties.getCluster().getNodes();
            List<RedisURI> uris = nodes.stream().map(node ->
                    RedisURI.builder().withHost(node.split(":")[0]).withPort(Integer.parseInt(node.split(":")[1])).withPassword(redisProperties.getPassword()).build()).collect(Collectors.toList());
            RedisClusterClient redisClient = RedisClusterClient.create(uris);
            RedisClusterRateLimiterFactory redisRateLimiterFactory = new RedisClusterRateLimiterFactory(redisClient);

            return redisRateLimiterFactory.getInstance(rules);
        }
    }
}
