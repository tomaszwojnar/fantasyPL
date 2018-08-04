package com.twojnar.fantasy.team;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;


@Service
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	List<Team> teams = new ArrayList<Team>();
	
	
	public List<Team> getTeams() { 
		return this.teams;
	}
	
	public void addTeam() {
		
	}

	public void setTeams(List<Team> teams) {
		teams.clear();
		this.teams = teams;
	}
	
	
	

}
