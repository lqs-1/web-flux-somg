package com.lqs.webflux.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

// 开启mongodb的Spring-data-jpa
@EnableReactiveMongoRepositories
@SpringBootApplication
public class WebFluxCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxCommonApplication.class, args);
	}

}
