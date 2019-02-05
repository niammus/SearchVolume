package com.sallics.search.volume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author mustafa
 *
 * This the main class for running spring-boot application
 *
 *
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class SearchVolumeApplication {

    public static void main(String[] args) {

		SpringApplication.run(SearchVolumeApplication.class, args);


	}



}

