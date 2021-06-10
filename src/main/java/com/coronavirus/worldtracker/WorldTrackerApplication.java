package com.coronavirus.worldtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WorldTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorldTrackerApplication.class, args);
	}

}
