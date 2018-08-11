package com.twojnar.fantasy.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@EnableMongoRepositories
@Service
public class FixtureService {
	
	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private TeamService teamService;
	
	
	private List<Fixture> fixtures = new ArrayList<Fixture>();
	
	
	public void updateFromDB() {
		this.fixtures = fixtureRepository.findAll();
	}

	public void addFixture(Fixture fixture) {
		fixtures.add(fixture);
	}

	public void setFixtures(List<Fixture> fixtures) {
		fixtures.clear();
		this.fixtures = fixtures;
	}
	
	public void saveFixtures() {
		this.fixtures.stream().forEach(e -> this.completeTeamInfo(e));
		fixtureRepository.saveAll(this.fixtures);
	}
	
	public void updateFixtures(List<Fixture> updatedFixtures) {
		for (Fixture updatedFixture : updatedFixtures) {
			this.fixtures.stream()
				.filter(e -> e.equals(updatedFixture)).findFirst().ifPresent(x -> {
					updatedFixture.setId(x.getId());
					this.fixtures.remove(x);
					this.fixtures.add(updatedFixture);
				});
		}
	}
	
	public void completeTeamInfo(Fixture fixture) {
		fixture.setAwayTeam(teamService.getTeamByFantasyId(fixture.getAwayTeam().getFantasyId()));
		fixture.setHomeTeam(teamService.getTeamByFantasyId(fixture.getHomeTeam().getFantasyId()));
		
	}
	
	public void initialLoad(List<Fixture> fixtures) {
		fixtureRepository.deleteAll();
		for (Fixture updatedFixture : fixtures) {
				this.fixtures.add(updatedFixture);
		};
		this.saveFixtures();
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}
	
	public Fixture getFixtureByFantasyId(int id) {
		return this.fixtures.stream()
		.filter(e -> e.getFantasyId() == id).collect(Collectors.toList()).get(0);
	}

}
