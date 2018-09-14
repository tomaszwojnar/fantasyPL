package com.twojnar.fantasy.squad;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.twojnar.fantasy.common.View;

@RestController
public class SquadController{
	
	@Autowired
	SquadService squadService;
	
	@RequestMapping(value = "/api/squads/{squad_id}", method = RequestMethod.GET)
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity getSquad(@PathVariable("squad_id") int squadId) throws JSONException, JsonParseException, JsonMappingException, IOException {
			Squad squad = squadService.getUpdatedSquad(squadId);
			if (squad != null) return ResponseEntity.ok(squad);
			else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Squad not found.");
	}
	
	@RequestMapping(value = "/api/squads/{squad_id}", method = RequestMethod.PUT)
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity addSquad(@PathVariable("squad_id") int squadId) throws JSONException, JsonParseException, JsonMappingException, IOException {
			try{
				Squad squad = squadService.getUpdatedSquad(squadId);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Squad already exists");
			}
			catch (NullPointerException e) {
				return ResponseEntity.ok(squadService.addSquad(squadId));
			}
	}
	
	@RequestMapping(value = "/api/squads/{squad_id}/lineups/{event_id}", method = RequestMethod.GET)
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity lineup(@PathVariable("squad_id") int squadId, @PathVariable("event_id") int eventId) throws JSONException, JsonParseException, JsonMappingException, IOException {
			try {
				Squad squad = squadService.getUpdatedSquad(squadId);
				return ResponseEntity.ok(squadService.getLineUp(squad, eventId));
			}
			catch (NullPointerException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Squad not found.");
			}
			catch (NoSuchElementException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event not yet played.");
			}
	}
	
	@RequestMapping(value = "/api/squads/{squad_id}/advice", method = RequestMethod.GET)
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity lineup(@PathVariable("squad_id") int squadId, @RequestParam(value = "method", required = false) String method) throws JSONException, JsonParseException, JsonMappingException, IOException {
		Squad squad = squadService.getUpdatedSquad(squadId);
		if (squad == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Squad not found.");
		try {
			return ResponseEntity.ok(squadService.getTransferAdvice(squad));
		}
		catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not yet played.");
		}
	}
}
