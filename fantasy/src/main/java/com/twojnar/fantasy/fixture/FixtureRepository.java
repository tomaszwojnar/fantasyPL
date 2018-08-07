package com.twojnar.fantasy.fixture;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.twojnar.fantasy.team.Team;

@Repository
public interface FixtureRepository extends MongoRepository<Fixture, String> {

	    public Team findByHomeTeam(Team team);
	    
	    public Team findByAwayTeam(Team team);
	    
}