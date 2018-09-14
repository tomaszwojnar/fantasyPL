package com.twojnar.fantasy.player.predictions;

import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.TeamService;



@Component
public class OverallStrengthSimilarOpponent extends AbstractPredictionMethod {
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FixtureService fixtureService;
	
	
	public double makePrediction(Player player, Fixture fixture) {
		
		if (!playerService.isPlayerInFixture(player, fixture)) throw new NoSuchElementException(player.getPlayerProfile().getLastName() + "does not play in fixture " + fixture.getFantasyId());
		
	    SimpleRegression simpleRegression = new SimpleRegression(true);

	    // passing data to the model
	    // model will be fitted automatically by the class 

			HistorySeason historySeason = playerService.getHistorySeason(player, "2017/18");
			
			if (historySeason != null)
					{
						historySeason.getHistoryPerformances().stream().forEach(q -> {
								int strengthDifference = (q.getWasHome()) ?
											q.getFixture().getHomeTeam().getStrength_overall_home() - q.getFixture().getAwayTeam().getStrength_overall_away():
											q.getFixture().getAwayTeam().getStrength_overall_away() - q.getFixture().getHomeTeam().getStrength_overall_home();
								double scoreForSimilarGames = 0;
								int ticker = 1;
								for (FullPerformance performance : historySeason.getHistoryPerformances()) {
									if (Math.abs(performance.getFixture().getHomeTeam().getStrength_overall_home() - performance.getFixture().getAwayTeam().getStrength_overall_away()-strengthDifference)<75)
									{
										scoreForSimilarGames = (scoreForSimilarGames + performance.getTotalPoints()) / ticker;
										ticker++;
									}
								}
								simpleRegression.addData(strengthDifference, scoreForSimilarGames);
						});
						
						int teamDifferential = player.getPlayerProfile().getTeam().equals(fixture.getHomeTeam()) ? fixtureService.getOverallHomeTeamAdvantage(fixture) : -fixtureService.getOverallHomeTeamAdvantage(fixture);
						
						
						return simpleRegression.predict(teamDifferential);
					}
			else throw new NoSuchElementException("No Last Season Data for player " + player.getPlayerProfile().getCode());
	}
			
	public void gatherData(Player player, Fixture fixture) {
		// TODO Auto-generated method stub
		
	}
	
	

	

}