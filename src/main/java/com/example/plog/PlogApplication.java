package com.example.plog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class PlogApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PlogApplication.class, args);
	}

}
