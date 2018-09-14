package com.twojnar.fantasy;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.twojnar.fantasy.common.ApplicationException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerProfile;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.PreviousGamesAttackDefence;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

import org.junit.Assert;

@RunWith(MockitoJUnitRunner.class)
public class PreviousGamesAttackDefenceTest {
	
	
	@Mock
	TeamService teamService;
	
	@Mock
	PlayerService playerService;
	
	@Mock
	FixtureService fixtureService;
	
	@Mock
	FantasyStatus fantasyStatus;
	
	@InjectMocks
	PreviousGamesAttackDefence previousGames;
	
	@Mock
	Player player;
	
	@Mock
	Fixture fixture;
	
	@Mock
	PlayerProfile playerProfile;
	
	@Mock
	Team team;
	
	@Mock
	Fixture playedNotProcessedFixture;
	
	@Mock
	Fixture playedAndProcessedFixture;
	
	@Mock
	FullPerformance processedButNotPlayed;
	
	@Mock
	FullPerformance processedAndPlayed;
	
	@Before
	public void before() {
		Random generator = new Random();
		Mockito.when(playerService.isPlayerInFixture(player, fixture)).thenReturn(true);
		Mockito.when(player.getPlayerProfile()).thenReturn(playerProfile);
		Mockito.when(player.getPlayerProfile().getTeam()).thenReturn(team);
		Mockito.when(playerProfile.getLastName()).thenReturn("Cech");
		Mockito.when(fixtureService.getHomeTeamAdvantageByPosition(fixture, player.getPlayerProfile().getPosition())).thenReturn(100);
		Mockito.when(fixture.getEvent()).thenReturn(5);

	}

	@Test(expected = ApplicationException.class)
	public void testMakePredictionException() {
		Mockito.when(playerService.isPlayerInFixture(player, fixture)).thenReturn(false);
		Mockito.when(fixture.getFantasyId()).thenReturn(1);
		previousGames.makePrediction(player, fixture);
	}
	
	@Test
	public void testHomeGame() {
		Mockito.when(fixtureService.isHome(fixture, team)).thenReturn(true);
		Assert.assertEquals(Double.NaN, previousGames.makePrediction(player, fixture), 1e-15);
	}
		
	@Test
	public void testMakePredictionRound1() {
		Mockito.when(fixture.getEvent()).thenReturn(1);
		Assert.assertEquals(Double.NaN, previousGames.makePrediction(player, fixture), 1e-15);
	}
	
	@Test
	public void testMakePredictionXRounds() {
		List<FullPerformance> processedPerformances = new ArrayList<FullPerformance>();
		Mockito.when(playedNotProcessedFixture.getKickoffTime()).thenReturn(new Date());
		Mockito.when(processedButNotPlayed.getFixture()).thenReturn(playedNotProcessedFixture);
		Mockito.when(playedAndProcessedFixture.getKickoffTime()).thenReturn(Date.from(new Date().toInstant().minusSeconds(1000000)));
		Mockito.when(processedAndPlayed.getFixture()).thenReturn(playedAndProcessedFixture);
		Mockito.when(processedAndPlayed.getTotalPoints()).thenReturn(5);
		Mockito.when(fixtureService.getHomeTeamAdvantageByPosition(playedAndProcessedFixture, player.getPlayerProfile().getPosition())).thenReturn(100);
		processedPerformances.add(processedButNotPlayed);
		processedPerformances.add(processedAndPlayed);
		for (int j = 5 - 1; j > 0; j--) {
			Mockito.when(playerService.getPerformancesByPlayerAndRound(player, j)).thenReturn(processedPerformances);
		}
		
		Assert.assertEquals(Double.NaN, previousGames.makePrediction(player, fixture), 1e-15);
	}

}
