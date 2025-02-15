package com.devaemlak.advertisement_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
@EnableRabbit
public class AdvertisementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertisementServiceApplication.class, args);
	}

}
