package com.twojnar.fantasy.team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
					updatedTeam.setFantasyId2017(x.getFantasyId2017());
					this.teams.remove(x);
					this.teams.add(updatedTeam);
				});;

		}
	}
	
	public void initialLoad(List<Team> teams) {
		teamRepository.deleteAll();
		this.teams.clear();
		for (Team updatedTeam : teams) {
				this.teams.add(updatedTeam);
		};
		this.saveTeams();
	}
	
	public Team getTeamByFantasyId2018(int id) {
		return this.teams.stream()
		.filter(e -> e.getFantasyId2018() == id).collect(Collectors.toList()).get(0);
	}
	
	public Team getTeamByFantasyId2017(int id) {
		return this.teams.stream()
		.filter(e -> e.getFantasyId2017() == id).collect(Collectors.toList()).get(0);
	}
	
	public Team getTeamByCode(int code) {
		return this.getTeams().stream()
		.filter(e -> e.getCode() == code).collect(Collectors.toList()).get(0);
	}
	
	public Team findByName(String n) {
			return this.teams.stream()
				.filter(e -> e.getName().equalsIgnoreCase(n)).findFirst().get();
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
}
