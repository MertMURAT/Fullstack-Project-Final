package com.devaemlak.discovery_client_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryClientServiceApplication.class, args);
	}

}
