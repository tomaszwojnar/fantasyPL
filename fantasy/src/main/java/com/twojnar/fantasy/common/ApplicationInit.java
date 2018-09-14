package com.twojnar.fantasy.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.EventService;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.AttackDefenceRegressionLastSeason;
import com.twojnar.fantasy.player.predictions.OverallStrengthSimilarOpponent;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
import com.twojnar.fantasy.squad.SquadService;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.taskRunner.TaskRunner;

@Component
@EnableScheduling
public class ApplicationInit {
	
	@Autowired
	TaskRunner taskRunner;
	
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
	
	@Autowired
	AttackDefenceRegressionLastSeason atackdeflast;
	
	@Autowired
	OverallStrengthSimilarOpponent overallstrength;
	
	@Autowired
	SimpleRegressionPastSeason simpleRegressionPast;
	
	static final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

	public void run() throws Exception {
		
		fantasyStatus.updateStatus();
		teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	eventService.updateFromDB();
    	playerService.updateFromDB();
    	
    	//taskRunner.run();
	}

}
