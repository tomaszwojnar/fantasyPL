package com.twojnar.taskRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.twojnar.batcher.CSVReaderWithHeaderAutoDetection;
import com.twojnar.fantasy.common.ApplicationInit;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.AttackDefenceRegressionLastSeason;
import com.twojnar.fantasy.player.predictions.OverallStrengthSimilarOpponent;
import com.twojnar.fantasy.player.predictions.PreviousGamesAttackDefence;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
import com.twojnar.fantasy.team.TeamService;


@Service
public class TaskRunner implements Runnable {
	
	static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);
	
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
	DataCleanDefinition dataCleanDefinition;

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
		//dataCleanDefinition.cleanPredictions();
		//dataCleanDefinition.clean5thRound();
		fantasyStatus.updateStatus();
		logger.info("Status update completed successfully");
		teamUpdateDefinition.updateAll();
		logger.info("TeamUpdateDefinition completed successfully");
		fixtureUpdateDefinition.updateAll();
		logger.info("FixtureUpdateDefinition completed successfully");
		playerUpdateDefinition.updateAll();
		logger.info("PlayerUpdateDefinition completed successfully");
		playerService.makePredictionsForAllPlayers(10, previousGames);
		logger.info("MakePredictions completed successfully");
		}
		catch (Exception e) {
			logger.error("TaskRunner failed.");
			logger.error(e.getMessage());
		}
	}
}


