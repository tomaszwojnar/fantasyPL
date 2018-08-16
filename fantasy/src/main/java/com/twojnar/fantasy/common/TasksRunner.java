package com.twojnar.fantasy.common;


import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twojnar.batcher.CSVReaderWithHeaderAutoDetection;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;


@Service
public class TasksRunner {
	
	@Autowired
	FixtureUpdateDefinition fixtureUpdateDefinition;
	
	@Autowired
	TeamsUpdateDefinition teamUpdateDefinition;
	
	@Autowired
	PlayerUpdateDefinition playerUpdateDefinition;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;

	@Autowired
	CSVReaderWithHeaderAutoDetection CSVReader;
	
	public void run(String... args) throws Exception {
		
		//teamUpdateDefinition.initialLoad();
		//teamUpdateDefinition.updateTeams();
		//fixtureUpdateDefinition.initialLoad();
		//CSVReader.processTeamsCSV("D:/IT Projects/fantasy/teams.csv");
		//teamService.saveTeams();
		//playerUpdateDefinition.initialLoad();
		//playerUpdateDefinition.updateHistorySeasons();
		//playerUpdateDefinition.updatePerformances();
		System.out.println("Done");
	
	}}


