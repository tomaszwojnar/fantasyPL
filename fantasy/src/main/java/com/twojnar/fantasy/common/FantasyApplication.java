package com.twojnar.fantasy.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.twojnar.fantasy.player.PlayerRepository;
import com.twojnar.fantasy.player.Player;

@SpringBootApplication
@ComponentScan("com.twojnar.fantasy.player")
@ComponentScan("com.twojnar.fantasy.fixture")
@ComponentScan("com.twojnar.fantasy.team")
@ComponentScan("com.twojnar.fantasy.squad")

public class FantasyApplication {
	
	@Autowired
	PlayerRepository playerRepository;

	public static void main(String[] args) {
		
		SpringApplication.run(FantasyApplication.class, args);
	}

}
