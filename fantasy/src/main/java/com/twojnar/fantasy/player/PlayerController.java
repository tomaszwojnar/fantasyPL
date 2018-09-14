package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.ApplicationException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.common.View;


@RestController
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	@JsonView(View.PublicDetails.class)
	@RequestMapping(value = "/api/players/{player_id}/predictions/{event_id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getPrediction(@PathVariable int player_id, @PathVariable int event_id) throws JSONException {
		Player player;
		try{
			player = playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason());
		}
		catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok(
				player
				.getPerformances()
				.stream()
				.filter(x -> x.getRound() == event_id)
				.map(x -> playerService.getLatestPredictionPerMethod(x))
				.collect(Collectors.toList())
				);
	}
	
	@JsonView(View.PublicDetails.class)
	@RequestMapping(value = "/api/players/{player_id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getPlayer(@PathVariable int player_id) throws JSONException {
		try{
			return ResponseEntity.ok(playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason()).getPlayerProfile());
		}
		catch (ApplicationException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		}

	
	@RequestMapping(value = "/api/players", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity getPlayers(
										@RequestParam("position") Optional<Integer> playerPosition,
										@RequestParam("lastName") Optional<String> lastName,
										@RequestParam("team") Optional<String> team
									) throws JSONException {
		if (!playerPosition.isPresent() && !lastName.isPresent() && !team.isPresent()) {
			return ResponseEntity.badRequest().body("Either playerPosition or lastName or team Query Parameter is required");
		}
		List<PlayerProfile> playerProfiles = playerService.getPlayers().stream().map(x -> x.getPlayerProfile()).collect(Collectors.toList());
		if (playerPosition.isPresent()) {
			playerProfiles.removeAll(playerProfiles.stream().filter(x -> x.getPosition() != playerPosition.get()).collect(Collectors.toList()));
		}
		if (lastName.isPresent()) {
			playerProfiles.removeAll(playerProfiles.stream().filter(x -> !lastName.get().equalsIgnoreCase(x.getLastName())).collect(Collectors.toList()));
		}
		if (team.isPresent()) {
			playerProfiles.removeAll(playerProfiles.stream().filter(x -> !team.get().equalsIgnoreCase(x.getTeam().getName())).collect(Collectors.toList()));
		}
		return ResponseEntity.ok(playerProfiles);
	}
	
	@JsonView(View.PublicGeneral.class)
	@RequestMapping(value = "/api/players/{player_id}/performances/{event_id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getPerformance(@PathVariable int player_id, @PathVariable int event_id) throws JSONException {
		
		try{
			return ResponseEntity.ok(
				playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason()).getPerformances().stream().filter(x -> x.getRound() == event_id).collect(Collectors.toList())
				);
		}
		catch (ApplicationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/api/players/{player_id}/performances", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getPerformances(@PathVariable int player_id) throws JSONException {
		return ResponseEntity.ok(
				playerService.getPlayerByFantasyIdAndSeason(player_id, fantasyStatus.getCurrentSeason()).getPerformances());
	}
	
}