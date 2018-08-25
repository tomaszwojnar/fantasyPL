package com.twojnar.fantasy.common;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.EventService;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.AttackDefenceRegressionLastSeason;
import com.twojnar.fantasy.player.predictions.OverallStrengthSimilarOpponent;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.player.predictions.PreviousGamesAttackDefence;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
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
	

	public void run() throws Exception {

		teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	eventService.updateFromDB();
    	playerService.updateFromDB();
    	
    	taskRunner.run();
    	
    	
    	/*for (Player player : playerService.getPlayers()) {
    		player.getPerformances().stream().forEach(x -> System.out.println(x.getLatestPredictionByMethod(previousGames.getClass().getName())));
    	}*/
    	
    	//for (Player player : playerService.getPlayers()) {
    	//	try {
    	//		playerService.makePredictions(player, 5, previousGames);
        //		System.out.println(player.getPlayerProfile().getLastName());
    	//	}
    	//	catch (Exception e) {
    	//		e.printStackTrace();
    	//	}
    	//
    	//}
    	
    	//statsTest.run();
    	
	}

}
