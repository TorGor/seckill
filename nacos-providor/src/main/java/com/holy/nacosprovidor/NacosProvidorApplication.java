package com.holy.nacosprovidor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosProvidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProvidorApplication.class, args);
    }

}
