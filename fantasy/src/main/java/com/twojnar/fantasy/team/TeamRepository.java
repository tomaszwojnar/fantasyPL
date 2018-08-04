package com.twojnar.fantasy.team;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.twojnar.fantasy.player.Player;

@Repository
public interface TeamRepository extends MongoRepository<Team, String> {
	
		boolean existsByName(String Name);

	    public Team findByName(String name);
}
