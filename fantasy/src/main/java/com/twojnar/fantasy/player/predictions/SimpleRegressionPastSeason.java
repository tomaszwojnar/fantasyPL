package com.twojnar.fantasy.player.predictions;

import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;



@Component
public class SimpleRegressionPastSeason extends AbstractPredictionMethod {
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	
	public double makePrediction(Player player, Fixture fixture) {
		
		if (!fixtureService.isPlayerInTeams(player, fixture)) throw new NoSuchElementException(player.getPlayerProfile().getLastName() + "does not play in fixture " + fixture.getFantasyId());
		
	    SimpleRegression simpleRegression = new SimpleRegression(true);

	    // passing data to the model
	    // model will be fitted automatically by the class 

			HistorySeason historySeason;

			historySeason = player.getHistorySeasons().stream().filter(x -> x.getSeasonName().equals("2017/18")).findFirst().get();

			String team = historySeason.getTeam();
			Team foundTeam = null;
			try {
				foundTeam = teamService.findByName(team);
			}
				catch (NoSuchElementException e) {	
			}
			
			int strengthHome = (foundTeam != null) ? foundTeam.getStrength_overall_home() : 1000;
			int strengthAway = (foundTeam != null) ? foundTeam.getStrength_overall_away() : 1000;
					
					
			historySeason.getHistoryPerformances().stream().filter(x-> x.getMinutes() > 45).forEach(y -> {
		        int strengthTeam = (y.getWasHome()) ? strengthHome : strengthAway;
				simpleRegression.addData(strengthTeam - y.getOpponentStrength(), y.getTotalPoints());
				});
			
			int teamDifferential = player.getPlayerProfile().getTeam().equals(fixture.getHomeTeam()) ? fixture.getOverallHomeTeamAdvantage() : -fixture.getOverallHomeTeamAdvantage();
			
			System.out.println(teamDifferential);
			
			return simpleRegression.predict(teamDifferential);
	}
			
	public void gatherData(Player player, Fixture fixture) {
		// TODO Auto-generated method stub
		
	}

	

}
