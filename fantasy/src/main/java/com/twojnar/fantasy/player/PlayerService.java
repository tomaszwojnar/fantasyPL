package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableMongoRepositories(basePackageClasses = PlayerRepository.class)
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	List<Player> players = new ArrayList<Player>();
	
	public List<Player> getPlayers() { 
		return playerRepository.findAll();
	}
}
