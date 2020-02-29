package com.holy.nacosconsumerone.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class MyRestTemplete {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateCommon(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(50))
                .setReadTimeout(Duration.ofSeconds(50))
                .build();
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplateCommon1(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(50))
                .setReadTimeout(Duration.ofSeconds(50))
                .build();
    }
}
