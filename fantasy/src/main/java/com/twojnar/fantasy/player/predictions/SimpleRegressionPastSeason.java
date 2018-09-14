package com.twojnar.fantasy.player.predictions;

import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.TeamService;



@Component
public class SimpleRegressionPastSeason extends AbstractPredictionMethod {
	
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
						historySeason.getHistoryPerformances().stream().forEach(y -> {
					        int strengthTeam = (y.getWasHome()) ? y.getFixture().getHomeTeam().getStrength_overall_home() : y.getFixture().getAwayTeam().getStrength_overall_away();
					        int opponentStrength = (y.getWasHome()) ? y.getFixture().getAwayTeam().getStrength_overall_away() : y.getFixture().getHomeTeam().getStrength_overall_home();
							simpleRegression.addData(strengthTeam - opponentStrength, y.getTotalPoints());
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