package com.twojnar.fantasy.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

@EnableMongoRepositories
@Service
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	private List<Team> teams = new ArrayList<Team>();
	
	public void updateFromDB() {
		this.teams = teamRepository.findAll();
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public void setTeams(List<Team> teams) {
		teams.clear();
		this.teams = teams;
	}
	
	public void saveTeams() {
		teamRepository.saveAll(this.teams);
	}
	
	public void updateTeams(List<Team> updatedTeams) {
		for (Team updatedTeam : updatedTeams) {
			this.teams.stream()
				.filter(e -> e.equals(updatedTeam)).findFirst().ifPresent(x -> {
					updatedTeam.setId(x.getId());
					this.teams.remove(x);
					this.teams.add(updatedTeam);
				});;

		}
	}
	
	public void initialLoad(List<Team> teams) {
		teamRepository.deleteAll();
		for (Team updatedTeam : teams) {
				this.teams.add(updatedTeam);
		};
		this.saveTeams();
	}
	
	public Team getTeamByFantasyId(int id) {
		return this.teams.stream()
		.filter(e -> e.getFantasyId() == id).collect(Collectors.toList()).get(0);
	}
	
	

	public List<Team> getTeams() {
		return teams;
	}
	
	
}
