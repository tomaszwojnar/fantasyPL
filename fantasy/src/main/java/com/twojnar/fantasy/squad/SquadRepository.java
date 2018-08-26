package com.twojnar.fantasy.squad;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SquadRepository extends MongoRepository<Squad, String> {

	Squad findByFantasyId(int id);
	
}
