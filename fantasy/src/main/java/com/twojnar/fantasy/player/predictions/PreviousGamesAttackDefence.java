package com.twojnar.fantasy.player.predictions;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.common.ApplicationException;
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
		
		if (!playerService.isPlayerInFixture(player, fixture)) {
			throw new ApplicationException(player.getPlayerProfile().getLastName() + "does not play in fixture " + fixture.getFantasyId());
		}
	    
		SimpleRegression simpleRegression = new SimpleRegression(true);
		
		int fixtureStrengthDifference = (fixtureService.isHome(fixture, player.getPlayerProfile().getTeam())) ?
					fixtureService.getHomeTeamAdvantageByPosition(fixture, player.getPlayerProfile().getPosition()) :
					-fixtureService.getHomeTeamAdvantageByPosition(fixture, player.getPlayerProfile().getPosition());
		
	    for (int i = fixture.getEvent()-1; i > 0; i--) {
	    	
	    	List<FullPerformance> processedPerformances = playerService.getPerformancesByPlayerAndRound(player, i);
	    	
			List<FullPerformance> playedPerformances = processedPerformances
														.stream()
														.filter(x -> x.getFixture().getKickoffTime().before(Date.from(new Date().toInstant().minusSeconds(86400))))
														.collect(Collectors.toList());
			for (FullPerformance playedPerformance : playedPerformances) {   	
		    		int strengthDifference = (fixtureService.isHome(playedPerformance.getFixture(), player.getPlayerProfile().getTeam())) ?
							fixtureService.getHomeTeamAdvantageByPosition(playedPerformance.getFixture(), player.getPlayerProfile().getPosition()) :
							-fixtureService.getHomeTeamAdvantageByPosition(playedPerformance.getFixture(), player.getPlayerProfile().getPosition());;
		    	simpleRegression.addData(strengthDifference, playedPerformance.getTotalPoints());
		    	}
		    }
	    double result = simpleRegression.predict(fixtureStrengthDifference);
	    return result;
	}
}

