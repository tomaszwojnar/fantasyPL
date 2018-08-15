package com.twojnar.fantasy.squad;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SquadController {
	
	@RequestMapping(value = "/squad", method = RequestMethod.POST)
	
	@ResponseBody
	public Object login(@RequestBody Map<String, Object> payload) throws JSONException {
		return payload.get("fantasyId");
	}

}
