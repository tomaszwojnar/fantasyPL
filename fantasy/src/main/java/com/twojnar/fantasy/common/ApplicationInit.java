package com.twojnar.fantasy.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.EventService;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.squad.SquadService;
import com.twojnar.fantasy.team.TeamService;

@Component
@EnableScheduling
public class ApplicationInit {
	
	@Autowired
	TasksRunner taskRunner;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	@Autowired
	PredictionService predictionService;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	StatsTests statsTest;
	
	@Autowired
	SquadService squadService;
	

	public void run() throws Exception {
		
		fantasyStatus.updateStatus();
		teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	eventService.updateFromDB();
    	playerService.updateFromDB();
    	
    	//taskRunner.run();
	}

}
