package com.twojnar.fantasy.player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


@RestController
public class PlayerController {
	
	@Autowired
	PlayerService playerService;
	
	@RequestMapping(value = "/predictions", method = RequestMethod.POST)
	
	@ResponseBody
	public Object login(@RequestBody Map<String, Object> payload) throws JSONException {
		return payload.get("fantasyId");
	}
	
}