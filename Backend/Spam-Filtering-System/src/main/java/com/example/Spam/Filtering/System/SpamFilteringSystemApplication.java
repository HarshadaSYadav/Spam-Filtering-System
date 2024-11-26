package com.example.Spam.Filtering.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpamFilteringSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpamFilteringSystemApplication.class, args);
		System.out.println("Spam Filtering Application is running...");
	}
	}

