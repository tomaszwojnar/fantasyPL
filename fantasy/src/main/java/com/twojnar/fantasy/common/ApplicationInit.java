package com.twojnar.fantasy.common;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.EventService;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.AttackDefenceRegressionLastSeason;
import com.twojnar.fantasy.player.predictions.OverallStrengthSimilarOpponent;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.player.predictions.PreviousGamesAttackDefence;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
import com.twojnar.fantasy.squad.Squad;
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
	SimpleRegressionPastSeason simpleRegression;
	
	@Autowired
	AttackDefenceRegressionLastSeason lastSeasonRegress;
	
	@Autowired
	OverallStrengthSimilarOpponent overallSimilar;
	
	@Autowired
	PreviousGamesAttackDefence previousGames;
	
	@Autowired
	SquadService squadService;
	

	public void run() throws Exception {

		fantasyStatus.updateStatus();
		teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	eventService.updateFromDB();
    	playerService.updateFromDB();
    	

    	
    	//Squad squad = squadService.getSquadByFantasyId(1468690);
		//if (squad == null) {
		//	squadService.downloadData(1468690);
		//}
		//else System.out.println(squad.getOverall_points());
    	
    	//taskRunner.run();
    	
  
    	//for (Player player : playerService.getPlayers()) {
    	//	try {
    	//		playerService.makePredictions(player, 38, simpleRegression);
    	//		playerService.makePredictions(player, 38, lastSeasonRegress);
    	//		playerService.makePredictions(player, 38, overallSimilar);
    	//		
    	//		
    	//	}
    	//	catch (Exception e) {
    	//		e.printStackTrace();
    	//	}
    	//
    	//}
    	
    	System.out.println("Done");
    	
    	//statsTest.run();
    	
	}

}
