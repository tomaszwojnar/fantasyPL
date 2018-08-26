package com.twojnar.fantasy.squad;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class SquadController{
	
	@Autowired
	SquadService squadService;
	
	@RequestMapping(value = "/squad/{squad_id}", method = RequestMethod.GET)
	public Squad squad(@PathVariable("squad_id") int squadId) throws JSONException, JsonParseException, JsonMappingException, IOException {
		Squad squad = squadService.getSquadByFantasyId(squadId);
		if (squad == null) {
			return squadService.downloadData(squadId);
		}
		else return squad;
		
	}

}
