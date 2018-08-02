package com.twojnar.fantasy.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@RequestMapping(value = "/players", method = RequestMethod.GET, produces = "application/json")
	public Map retrievePlayersByCriteria(@RequestParam(value="name", required=true) String playerName) throws IOException {
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("Imię", playerName);
		return mapa;

	}
	
	@GetMapping(value = "/players/{playerId}")
	public List<HistorySeason> retrievePlayerById(@PathVariable int playerId) throws IOException {
		Map<String, String> mapa = new HashMap<String, String>();
		List<HistorySeason> seasons = new ArrayList<HistorySeason>();
		for (Player player : playerService.getPlayers()) {
			if (player.getFantasyId() == playerId) seasons =  player.getHistoricalSeasons();
		}
		return seasons;
	}
}