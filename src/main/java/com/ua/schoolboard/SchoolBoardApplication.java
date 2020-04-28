package com.ua.schoolboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.ua.schoolboard.persistence.repos")
@EntityScan("com.ua.schoolboard.persistence.model")
@SpringBootApplication
public class SchoolBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolBoardApplication.class, args);
	}

}
