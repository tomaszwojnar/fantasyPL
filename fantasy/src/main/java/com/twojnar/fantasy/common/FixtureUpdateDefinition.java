package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.FantasyFixtureResponse;
import com.twojnar.scrapper.ScrapperService;

@Component
public class FixtureUpdateDefinition extends TaskDefinition {
	
	@Autowired
	ScrapperService scrapper;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FantasyFixtureResponse fixtureResponse;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	public void updateFixtures() throws IOException {
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		List<Fixture> fixtures = (List<Fixture>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/fixtures", fixtureResponse);
		fixtures.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		fixtureService.updateFixtures(fixtures);
		fixtureService.saveFixtures();
	}
	
	public void initialLoad() throws IOException {
		teamService.updateFromDB();
		List<Fixture> fixtures = (List<Fixture>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/fixtures", fixtureResponse);
		fixtures.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		fixtureService.initialLoad(fixtures);
	}
}
