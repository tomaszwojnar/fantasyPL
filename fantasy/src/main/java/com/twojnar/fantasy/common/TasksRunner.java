package com.twojnar.fantasy.common;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.twojnar.fantasy.player.predictions.AttackDefenceRegressionLastSeason;
import com.twojnar.fantasy.player.predictions.OverallStrengthSimilarOpponent;
import com.twojnar.fantasy.player.predictions.PreviousGamesAttackDefence;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
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
	
	@Autowired
	SimpleRegressionPastSeason simpleRegression;
	
	@Autowired
	AttackDefenceRegressionLastSeason lastSeasonRegress;
	
	@Autowired
	OverallStrengthSimilarOpponent overallSimilar;
	
	@Autowired
	PreviousGamesAttackDefence previousGames;
	
	
	@Override
	@Scheduled(cron = "${schedulerCron}")
	public void run() {
		try {
		fantasyStatus.updateStatus();
		teamUpdateDefinition.updateTeams();
		fixtureUpdateDefinition.updateFixtures();
		fixtureUpdateDefinition.updateEvents();
		playerUpdateDefinition.updateProfiles();
		playerUpdateDefinition.updatePerformances();
		playerService.makePredictionsForAllPlayers(10, previousGames);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}


