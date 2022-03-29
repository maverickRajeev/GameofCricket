package com.tekion.cricketGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableCaching
@ComponentScan("com.tekion")
public class CricketGameApplication {
	public static void main(String[] args) {
		SpringApplication.run(CricketGameApplication.class, args);
	}
}

