package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twojnar.batcher.CSVReaderWithHeaderAutoDetection;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.PlayerService;
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
	CSVReaderWithHeaderAutoDetection CSVReader;
	
	public void run(String... args) throws Exception {
		
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
		playerUpdateDefinition.updatePerformances();
		
	}
}
