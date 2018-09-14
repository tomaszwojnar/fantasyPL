package com.twojnar.fantasy.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public ResponseEntity apiInfo() {
		return ResponseEntity.ok("Welcome to fplHelp. <br> The project aims to help fpl managers make good decisions about player transfers by providing them with the comparison of their players potential score and the potential score of other player.<br>"
				+ "<br>The project is in progress so there is no fancy frontend but you can explore the following resources:"
				+ "<br>GET /api/players - find a player using the lastName, position or team - e.g. /api/players?position=2&team=Arsenal"
				+ "<br>GET /api/players/{player_id} - get player data on the basis of their fantasyId - e.g. /api/players/1"
				+ "<br>GET /api/players/{player_id}/predictions/{event_id} - get predictions for a given player and event (1-38) - e.g. /api/players/1/predictions/5"
				+ "<br>GET /api/players/{player_id}/performances/{event_id} - get performances for a given player and event (1-38) - e.g. /api/players/1/performances/5"
				+ "<br>GET /api/squads/{squad_id} - get information about a squad from fantasyPL e.g. /api/squads/1468689"
				+ "<br>PUT /api/squads/{squad_id} - download information about a squad from fantasyPL e.g. /api/squads/1468689"
				+ "<br>GET /api/squads/{squad_id}/advice - get transfer advice for a given squad by squadsFantasyId e.g. /api/squads/1468689/advice"
				+ "<br>GET /api/fixtures - find a fixture using the round, fixtureCode or team - e.g. /api/players?round=2&team=Arsenal"
				+ "<br>GET /api/fixtures/{fixtureCode} - get detailed fixture information based on fixtureCode /api/fixtures/918893");
	}
}
