package com.employmee.employmee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmploymeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmploymeeApplication.class, args);
	}

}
