package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Performance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerProfile;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.FantasyPlayerDetailsResponse;
import com.twojnar.scrapper.FantasyPlayerPerformancesResponse;
import com.twojnar.scrapper.FantasyPlayerProfileResponse;
import com.twojnar.scrapper.ScrapperService;

@Component
public class PlayerUpdateDefinition extends TaskDefinition {
	
	@Autowired
	ScrapperService scrapper;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	FantasyPlayerProfileResponse playerProfileResponse;
	
	@Autowired
	FantasyPlayerDetailsResponse playerDetailsResponse;
	
	@Autowired
	FantasyPlayerPerformancesResponse playerPerformanceResponse;
	
	public void initialLoad() throws JsonParseException, JsonMappingException, IOException {
		List<PlayerProfile> playerProfiles = (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", playerProfileResponse);
		playerService.initialLoad(playerProfiles);
	}
	
	public void updateProfiles() throws IOException {
		List<PlayerProfile> playerProfiles = (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", playerProfileResponse);
		for (PlayerProfile profile : playerProfiles) 
		{
			playerService.updateProfile(profile);
		}
		playerService.savePlayers();
	}
	
	public void updateHistorySeasons() throws IOException, JSONException {
		for (Player player : playerService.getPlayers()) {
			List<HistorySeason> historySeasons = (List<HistorySeason>) scrapper.scrapField(
				"https://fantasy.premierleague.com/drf/element-summary/" + player.getPlayerProfile().getFantasyId2018(), "history_past", playerDetailsResponse);
			playerService.updateHistorySeasons(player.getPlayerProfile().getCode(), historySeasons);
			playerService.savePlayers();
		}
	}
	
	public void updatePerformances() throws IOException, JSONException {
		for (Player player : playerService.getPlayers()) {
			List<FullPerformance> performances = (List<FullPerformance>) scrapper.scrapField(
				"https://fantasy.premierleague.com/drf/element-summary/" + player.getPlayerProfile().getFantasyId2018(), "history", playerPerformanceResponse);
			playerService.updatePerformances(player.getPlayerProfile().getCode(), performances);
			player.getPerformances().stream().forEach(x -> {
				playerService.completePerformanceData(x);
			});
			playerService.savePlayer(player);
		}
	}
	
	public List<PlayerProfile> getProfiles() throws IOException {
		return (List<PlayerProfile>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/elements", playerProfileResponse);
	}
}
