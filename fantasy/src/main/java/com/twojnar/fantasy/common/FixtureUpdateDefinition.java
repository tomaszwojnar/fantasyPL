package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Event;
import com.twojnar.fantasy.fixture.EventService;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.Response;
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
	Response response;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	@Autowired
	EventService eventService;
	
	public void updateFixtures() throws IOException {
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		List<Fixture> fixtures = (List<Fixture>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/fixtures", new Fixture());
		fixtures.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		fixtureService.updateFixtures(fixtures);
		fixtureService.saveFixtures();
	}
	
	public void updateEvents() throws IOException {
		List<Event> events = (List<Event>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/events", new Event());
		events.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		eventService.updateEvents(events);
		eventService.saveToDB();
	}
	
	public void initialLoad() throws IOException {
		teamService.updateFromDB();
		List<Fixture> fixtures = (List<Fixture>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/fixtures", new Fixture());
		fixtures.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		fixtureService.initialLoad(fixtures);
		List<Event> events = (List<Event>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/events", new Event());
		events.stream().forEach(x -> x.setSeason(fantasyStatus.getCurrentSeason()));
		eventService.initialLoad(events);
	}
}
