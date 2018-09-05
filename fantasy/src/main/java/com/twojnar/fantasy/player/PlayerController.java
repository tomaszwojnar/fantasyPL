package com.twojnar.fantasy.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twojnar.fantasy.common.FantasyStatus;


@RestController
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	
	@RequestMapping(value = "/api/player/{player_id}/prediction/{event_id}", method = RequestMethod.GET)
	
	@ResponseBody
	public ResponseEntity getPrediction(@PathVariable int player_id, @PathVariable int event_id) throws JSONException {
		return ResponseEntity.ok(
				playerService
				.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason())
				.getPerformances()
				.stream()
				.filter(x -> x.getRound() == event_id)
				.map(x -> x.getLatestPredictionPerMethod())
				.collect(Collectors.toList())
				);
	}
	
	@RequestMapping(value = "/api/player/{player_id}", method = RequestMethod.GET)
	
	@ResponseBody
	public ResponseEntity getPlayer(@PathVariable int player_id) throws JSONException {
		return ResponseEntity.ok(
				playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason()).getPlayerProfile()
				);
	}
	
	
	@RequestMapping(value = "/api/player/{player_id}/performance/{event_id}", method = RequestMethod.GET)
	
	@ResponseBody
	public ResponseEntity getPerformance(@PathVariable int player_id, @PathVariable int event_id) throws JSONException {
		return ResponseEntity.ok(
				playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason()).getPerformances().stream().filter(x -> x.getRound() == event_id).collect(Collectors.toList())
				);
	}
	
}