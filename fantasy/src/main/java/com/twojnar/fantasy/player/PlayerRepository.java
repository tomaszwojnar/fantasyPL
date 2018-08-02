package com.twojnar.fantasy.player;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

	    public List<Player> findByLastName(String lastName);
	}
