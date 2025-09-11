package com.brs.gridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GridgeApplication.class, args);
	}

}
