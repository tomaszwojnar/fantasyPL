package com.twojnar.fantasy.common;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.twojnar.batcher.CSVReaderWithHeaderAutoDetection;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Performance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;


@Service
public class TasksRunner implements Runnable {
	
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
	
	@Override
	@Scheduled(cron = "0 15 * * * ?")
	public void run() {
		try {
		fantasyStatus.updateStatus();
		//teamUpdateDefinition.initialLoad();
		teamUpdateDefinition.updateTeams();
		
		//fixtureUpdateDefinition.initialLoad();
		fixtureUpdateDefinition.updateFixtures();
		fixtureUpdateDefinition.updateEvents();
		//CSVReader.processTeamsCSV("D:/IT Projects/fantasy/teams.csv");
		//CSVReader.processFixturesCSV("D:/IT Projects/fantasy/fixtures.csv");
		//CSVReader.processPlayerProfileCSV("D:/IT Projects/fantasy/players.csv");
		//CSVReader.processHistoryPerfomarnces("D:/IT Projects/fantasy/player_match_details.csv");
		//playerUpdateDefinition.initialLoad();
		//playerService.getPlayers().stream().forEach(x -> x.setPerformances(new ArrayList<FullPerformance>()));
		playerUpdateDefinition.updateProfiles();
		//teamService.saveTeams();
		playerUpdateDefinition.updatePerformances();
		//System.out.println("Done");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}


