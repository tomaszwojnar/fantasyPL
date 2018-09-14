package com.twojnar.fantasy.fixture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.twojnar.fantasy.common.ApplicationException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.common.ListUtil;
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
	
	/**
	 * For each updatedFixture the method matches an existing fixture and replaces the existing with new. The method does not persist the fixture or complete team data.
	 * 
	 * @param updatedFixtures
	 * @return void
	 */
	
	
	public void updateFixtures(List<Fixture> updatedFixtures) {
		for (Fixture updatedFixture : updatedFixtures) {
			Fixture existingFixture = ListUtil.getSingleFromList(this.fixtures.stream()
																		.filter(e -> e.equals(updatedFixture))
																		.collect(Collectors.toList())
																		);
			updatedFixture.setId(existingFixture.getId());
			this.fixtures.remove(existingFixture);
			this.fixtures.add(updatedFixture);
		}
	}
	
	/**
	 * The method completes the original team data (fantasyId only) with the full team data.
	 * 
	 * @throws NoSuchElementException
	 * @param fixture
	 * @return void
	 */
	
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
			throw new NoSuchElementException("Data for season " + fixture.getSeason() + " was requested but none was found");
		}
	}
	
	/**
	 * The method clears the list of fixtures, clears persisted fixtures, adds fixtures from the list and persists.
	 * 
	 * @param fixtures
	 * @return void
	 */
	
	
	public void initialLoad(List<Fixture> fixtures) {
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
	
	/**
	 * Returns a fixture by fantasyId and season name in "2018/19" format.
	 * @param id
	 * @param season
	 * @return
	 */
	
	public Fixture getFixtureByFantasyIdAndSeason(int id, String season) {
		return ListUtil.getSingleFromList(this.fixtures.stream()
						.filter(e -> e.getFantasyId() == id && e.getSeason().equalsIgnoreCase(season))
						.collect(Collectors.toList()));
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
					e.getAwayTeam().getCode() == team.getCode()
					|| e.getHomeTeam().getCode() == team.getCode()).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Method finds and returns the list of coming fixtures for a team.
	 * 
	 * @param team
	 * @param number - number of games to be returned
	 * @param season
	 * @return list of coming fixtures for the team
	 */
	
	public List<Fixture> getNextFixturesForTeam(Team team, int number, String season) {
		
		return this.getFixturesForTeam(team).stream()
									 .filter(x ->x.getEvent()>fantasyStatus.getCurrentEvent() && x.getSeason().equals(season))
									 .sorted(Comparator.comparingInt(Fixture::getEvent))
									 .limit(number)
									 .collect(Collectors.toList());

	}
	
	public Boolean isHome(Fixture fixture, Team team) {
		return fixture.getHomeTeam().equals(team);
	}
	
	
	
	public Fixture getFixtureByCode(int fixtureCode) {
		return ListUtil.getSingleFromList(this.getFixtures().stream().filter(x -> x.getCode() == fixtureCode).collect(Collectors.toList()));
	}
	
	
	/**
	 * 
	 * Method returns the difference between the strength of the opposing lines. Defence strength vs attack strength is considered for GKs, DEFs and ATT. All stats are considered for midfielders.
	 * 
	 * @param fixture Fixture to be considered
	 * @param position int specifying the position to be considered
	 * @return int home team advantage for the position
	 * @throws ApplicationException if position does not exist
	 */
	
	
	public int getHomeTeamAdvantageByPosition(Fixture fixture, int position) {
		Team homeTeam = fixture.getHomeTeam();
		Team awayTeam = fixture.getAwayTeam();
		switch (position) {
			case 1:
			case 2:
				return homeTeam.getStrength_defence_home() - awayTeam.getStrength_attack_away();
			case 3:
				return
					homeTeam.getStrength_defence_home() +
					homeTeam.getStrength_attack_home() -
					awayTeam.getStrength_defence_away() -
					awayTeam.getStrength_defence_away();
			case 4:
				return homeTeam.getStrength_attack_home() - awayTeam.getStrength_defence_away();
			default: throw new ApplicationException("Position does not exist");
		}
	}
	
	public int getOverallHomeTeamAdvantage(Fixture fixture) {
		return fixture.getHomeTeam().getStrength_overall_home() - fixture.getAwayTeam().getStrength_overall_home();
	}
}
