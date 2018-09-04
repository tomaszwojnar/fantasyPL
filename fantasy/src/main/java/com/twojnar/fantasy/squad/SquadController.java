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
	
	@RequestMapping(value = "/api/squad/{squad_id}", method = RequestMethod.GET)
	@JsonView(View.Public.class)
	public ResponseEntity squad(@PathVariable("squad_id") int squadId) throws JSONException, JsonParseException, JsonMappingException, IOException {
			try {
				return ResponseEntity.ok(squadService.getUpdatedSquad(squadId));
			}
			catch (NullPointerException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect team id.");
			}
	}
	
	@RequestMapping(value = "/api/squad/{squad_id}/lineups/{event_id}", method = RequestMethod.GET)
	@JsonView(View.Public.class)
	public ResponseEntity lineup(@PathVariable("squad_id") int squadId, @PathVariable("event_id") int eventId) throws JSONException, JsonParseException, JsonMappingException, IOException {
			try {
				return ResponseEntity.ok(squadService.getLineUp(squadId, eventId));
			}
			catch (NullPointerException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect team id.");
			}
			catch (NoSuchElementException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event not yet played.");
			}
	}
	
	@RequestMapping(value = "/api/squad/{squad_id}/advice", method = RequestMethod.GET)
	@JsonView(View.Public.class)
	public ResponseEntity lineup(@PathVariable("squad_id") int squadId, @RequestParam(value = "method", required = false) String method) throws JSONException, JsonParseException, JsonMappingException, IOException {
			try {
				return ResponseEntity.ok(squadService.getTransferAdvice(squadId));
				//return ResponseEntity.ok(squadService.getTransferAdviceUsingMethod(squadId, method));
			}	

			
			catch (NoSuchElementException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event not yet played.");
			}
	}
	
	

}
