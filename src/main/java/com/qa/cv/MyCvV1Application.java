package com.qa.cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyCvV1Application {

	public static void main(String[] args) {
		SpringApplication.run(MyCvV1Application.class, args);
	}
}
