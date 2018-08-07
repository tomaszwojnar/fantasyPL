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
	
	public void updateFixtures() throws IOException {
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		List<Fixture> fixtures = (List<Fixture>) scrapper.scrap("https://fantasy.premierleague.com/drf/fixtures", fixtureResponse);
		fixtureService.updateFixtures(fixtures);
		fixtureService.saveFixtures();
	}
}
