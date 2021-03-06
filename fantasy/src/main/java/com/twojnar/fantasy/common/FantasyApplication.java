package com.twojnar.fantasy.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.twojnar.fantasy.player")
@ComponentScan("com.twojnar.fantasy.fixture")
@ComponentScan("com.twojnar.fantasy.team")
@ComponentScan("com.twojnar.fantasy.squad")
@ComponentScan("com.twojnar.scrapper")
@ComponentScan("com.twojnar.batcher")
@ComponentScan("com.twojnar.taskRunner")
@EnableCaching
public class FantasyApplication implements CommandLineRunner {
	

	@Autowired
	ApplicationInit applicationInit;

	public static void main(String[] args) {
		
		SpringApplication.run(FantasyApplication.class);
	}

    public void run(String... args) throws Exception {
    	applicationInit.run();
    }
}
