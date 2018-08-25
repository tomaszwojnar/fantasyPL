package com.twojnar.fantasy.fixture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@EnableMongoRepositories
@Service
public class FixtureService {
	
	@Autowired
	private FixtureRepository fixtureRepository;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private FantasyStatus fantasyStatus;
	
	
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
		switch (fixture.getSeason()) {
		case "2018/19":
			fixture.setAwayTeam(teamService.getTeamByFantasyId2018(fixture.getAwayTeam().getFantasyId2018()));
			fixture.setHomeTeam(teamService.getTeamByFantasyId2018(fixture.getHomeTeam().getFantasyId2018()));
			break;
		case "2017/18" :
			fixture.setAwayTeam(teamService.getTeamByFantasyId2017(fixture.getAwayTeam().getFantasyId2017()));
			fixture.setHomeTeam(teamService.getTeamByFantasyId2017(fixture.getHomeTeam().getFantasyId2017()));
			break;
		default :
			throw new NoSuchElementException();
		}
	}
	
	public void initialLoad(List<Fixture> fixtures) {
		fixtureRepository.deleteAll();
		this.fixtures.clear();
		fixtureRepository.deleteAll();
		for (Fixture updatedFixture : fixtures) {
				this.fixtures.add(updatedFixture);
		};
		this.saveFixtures();
	}

	public List<Fixture> getFixtures() {
		return this.fixtures;
	}
	
	public Fixture getFixtureByFantasyIdAndSeason(int id, String season) {
		return this.fixtures.stream()
		.filter(e -> e.getFantasyId() == id && e.getSeason().equalsIgnoreCase(season)).collect(Collectors.toList()).get(0);
	}
	
	/**
	 * Returns unsorted list of Fixtures for the team.
	 * 
	 * @param team
	 * @return
	 */
	
	public List<Fixture> getFixturesForTeam(Team team) {
		return this.fixtures.stream()
		.filter(e ->
			e.getAwayTeam().getFantasyId2018() == team.getFantasyId2018()
			|| e.getHomeTeam().getFantasyId2018() == team.getFantasyId2018()).collect(Collectors.toList());
	}
	
	public List<Fixture> getNextFixturesForTeam(Team team, int number, String season) {
		
		List<Fixture> list = this.getFixturesForTeam(team).stream()
									 .filter(x ->x.getEvent()>fantasyStatus.getCurrentEvent() && x.getSeason().equals(season))
									 .sorted(Comparator.comparingInt(Fixture::getEvent))
									 .limit(number)
									 .collect(Collectors.toList());
		return list;

	}
	
	public Boolean isHome(Fixture fixture, Team team) {
		return fixture.getHomeTeam().equals(team);
	}

}
