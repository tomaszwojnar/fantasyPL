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
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;



@Component
public class AttackDefenceRegressionLastSeason extends AbstractPredictionMethod {
	
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
							int strengthDifference = 0;
							if (q.getWasHome()) 
								{
								Team playerTeam = q.getFixture().getHomeTeam();
								Team opponentTeam = q.getFixture().getAwayTeam();
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
								Team playerTeam = q.getFixture().getAwayTeam();
								Team opponentTeam = q.getFixture().getHomeTeam();
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
							simpleRegression.addData(strengthDifference, q.getTotalPoints());
							});
						
						int teamDifferential = 0;
							if (fixture.getAwayTeam().equals(player.getPlayerProfile().getTeam()))
							{
							Team playerTeam = fixture.getHomeTeam();
							Team opponentTeam = fixture.getAwayTeam();
							switch (player.getPlayerProfile().getPosition()) {
							case 1:
							case 2:
								teamDifferential = playerTeam.getStrength_defence_home() - opponentTeam.getStrength_attack_away();
								break;
							case 3:
								teamDifferential = 
									playerTeam.getStrength_defence_home() +
									playerTeam.getStrength_attack_home() -
									opponentTeam.getStrength_defence_away() -
									opponentTeam.getStrength_defence_away();
								break;
							case 4:
								teamDifferential = playerTeam.getStrength_attack_home() - opponentTeam.getStrength_defence_away();
								break;
							}
							}
						else {
							Team playerTeam = fixture.getAwayTeam();
							Team opponentTeam = fixture.getHomeTeam();
							switch (player.getPlayerProfile().getPosition()) {
							case 1:
							case 2:
								teamDifferential = playerTeam.getStrength_defence_away() - opponentTeam.getStrength_attack_home();
								break;
							case 3:
								teamDifferential = 
									playerTeam.getStrength_defence_away() +
									playerTeam.getStrength_attack_away() -
									opponentTeam.getStrength_defence_home() -
									opponentTeam.getStrength_defence_home();
								break;
							case 4:
								teamDifferential = playerTeam.getStrength_attack_away() - opponentTeam.getStrength_defence_home();
								break;
							}
		
						}
						return simpleRegression.predict(teamDifferential);
					}
			else throw new NoSuchElementException("No Last Season Data for player " + player.getPlayerProfile().getCode());
	}
			
	public void gatherData(Player player, Fixture fixture) {
		// TODO Auto-generated method stub
		
	}
	
	

	

}