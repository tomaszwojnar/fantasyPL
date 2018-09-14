package com.twojnar.taskRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerProfile;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.Prediction;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.ScrapperService;
import com.twojnar.taskRunner.TaskDefinition;

@Component
@SuppressWarnings({"unchecked"})
public class PlayerUpdateDefinition implements TaskDefinition {
	
	@Autowired
	ScrapperService scrapper;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	public void initialLoad() throws IOException {
		List<PlayerProfile> playerProfiles = (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", new PlayerProfile());
		playerService.initialLoad(playerProfiles);
		for (Player player : playerService.getPlayers()) {
			List<HistorySeason> historySeasons = new ArrayList<>();
			try {
			historySeasons = (List<HistorySeason>) scrapper.scrapField(
				"https://fantasy.premierleague.com/drf/element-summary/" + player.getPlayerProfile().getFantasyId2018(), "history_past", new PlayerProfile());
			}
			catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			playerService.updateHistorySeasons(player.getPlayerProfile().getCode(), historySeasons);
			playerService.savePlayers();
		}
	}
	
	public void updateProfiles() throws IOException {
		List<PlayerProfile> playerProfiles = (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", new PlayerProfile());
		playerProfiles.stream().forEach(x -> {
			x.setTeam(teamService.getTeamByFantasyId2018(x.getTeam().getFantasyId2018()));
			playerService.updateProfile(x);
			Player player = playerService.getPlayerByCode(x.getCode());
			Fixture nextFixture = fixtureService.getNextFixturesForTeam(x.getTeam(), 1, fantasyStatus.getCurrentSeason()).get(0);
			Prediction prediction = new Prediction(x.getCode(), nextFixture.getCode(), "FantasyPLWebsite", fantasyStatus.getCurrentEvent()+1,x.getExpectedPointsNextRound());
			FullPerformance performance;
			try {
				performance = playerService.getPerformanceBYPlayerAndFixture(player, nextFixture);
			}
			catch (NoSuchElementException e) {
				performance = new FullPerformance();
				performance.setFixture(nextFixture);
				performance.setRound(nextFixture.getEvent());
			}
			playerService.addPredictionToPlayerPerformance(prediction, performance);
		});
		playerService.savePlayers();
	}
	
	public void updatePerformances() throws IOException {
		for (Player player : playerService.getPlayers()) {
			List<FullPerformance> performances;
			try 
			{
				performances = (List<FullPerformance>) scrapper.scrapField(
				"https://fantasy.premierleague.com/drf/element-summary/" + player.getPlayerProfile().getFantasyId2018(), "history", new FullPerformance());
			}
			catch (Exception e) {
				continue;
			}

			playerService.updatePerformances(player.getPlayerProfile().getCode(), performances, true);
			playerService.savePlayer(player);
		}
	}
	
	public List<PlayerProfile> getProfiles() throws IOException {
		return (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", new PlayerProfile());
	}

	@Override
	public void updateAll() throws IOException {
		this.updateProfiles();
		this.updatePerformances();
	}
	

}
