package com.twojnar.fantasy.player.predictions;


import java.util.NoSuchElementException;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@Component
public class PreviousGamesAttackDefence extends AbstractPredictionMethod {
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	public double makePrediction(Player player, Fixture fixture) {
		
		if (!playerService.isPlayerInFixture(player, fixture)) throw new NoSuchElementException(player.getPlayerProfile().getLastName() + "does not play in fixture " + fixture.getFantasyId());
		
	    SimpleRegression simpleRegression = new SimpleRegression(true);
	    
	    int fixtureStrengthDifference = 0;

		if (fixtureService.isHome(fixture, player.getPlayerProfile().getTeam()))  
			{
			Team playerTeam = fixture.getHomeTeam();
			Team opponentTeam = fixture.getAwayTeam();
			switch (player.getPlayerProfile().getPosition()) {
			case 1:
			case 2:
				fixtureStrengthDifference = playerTeam.getStrength_defence_home() - opponentTeam.getStrength_attack_away();
				break;
			case 3:
				fixtureStrengthDifference = 
					playerTeam.getStrength_defence_home() +
					playerTeam.getStrength_attack_home() -
					opponentTeam.getStrength_defence_away() -
					opponentTeam.getStrength_defence_away();
				break;
			case 4:
				fixtureStrengthDifference = playerTeam.getStrength_attack_home() - opponentTeam.getStrength_defence_away();
				break;
			}
		}
		else {
			Team playerTeam = fixture.getAwayTeam();
			Team opponentTeam = fixture.getHomeTeam();
			switch (player.getPlayerProfile().getPosition()) {
			case 1:
			case 2:
				fixtureStrengthDifference = playerTeam.getStrength_defence_away() - opponentTeam.getStrength_attack_home();
				break;
			case 3:
				fixtureStrengthDifference = 
					playerTeam.getStrength_defence_away() +
					playerTeam.getStrength_attack_away() -
					opponentTeam.getStrength_defence_home() -
					opponentTeam.getStrength_defence_home();
				break;
			case 4:
				fixtureStrengthDifference = playerTeam.getStrength_attack_away() - opponentTeam.getStrength_defence_home();
				break;
			}
		}
	    for (int i = fixture.getEvent()-1; i > 0; i--) {
	    	FullPerformance processedPerformance = playerService.getPerformanceByRoundAndPlayer(player, i);
	    	
	    	if (processedPerformance != null && processedPerformance.getRound()<=fantasyStatus.getCurrentEvent()) {
	    		int strengthDifference = 0;
	    		if (processedPerformance.isHome(player.getPlayerProfile().getTeam()))
					{
					Team playerTeam = processedPerformance.getFixture().getHomeTeam();
					Team opponentTeam = processedPerformance.getFixture().getAwayTeam();
					switch (player.getPlayerProfile().getPosition()) {
					case 1:
					case 2:
						strengthDifference = playerTeam.getStrength_defence_home() - opponentTeam.getStrength_attack_away();
						break;
					case 3:
						strengthDifference = 
							playerTeam.getStrength_defence_home() +
							playerTeam.getStrength_attack_home() -
							opponentTeam.getStrength_defence_away() -
							opponentTeam.getStrength_defence_away();
						break;
					case 4:
						strengthDifference = playerTeam.getStrength_attack_home() - opponentTeam.getStrength_defence_away();
						break;
					}
				}
			else {
				Team playerTeam = processedPerformance.getFixture().getAwayTeam();
				Team opponentTeam = processedPerformance.getFixture().getHomeTeam();
				switch (player.getPlayerProfile().getPosition()) {
				case 1:
				case 2:
					strengthDifference = playerTeam.getStrength_defence_away() - opponentTeam.getStrength_attack_home();
					break;
				case 3:
					strengthDifference = 
						playerTeam.getStrength_defence_away() +
						playerTeam.getStrength_attack_away() -
						opponentTeam.getStrength_defence_home() -
						opponentTeam.getStrength_defence_home();
					break;
				case 4:
					strengthDifference = playerTeam.getStrength_attack_away() - opponentTeam.getStrength_defence_home();
					break;
				}

			}
	    	simpleRegression.addData(strengthDifference, processedPerformance.getTotalPoints());
	    }
	    }
	    double result = simpleRegression.predict(fixtureStrengthDifference);
	    return (result<0) ? Double.NaN : result;
	}
}

