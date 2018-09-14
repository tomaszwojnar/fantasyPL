package com.twojnar.fantasy.fixture;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.twojnar.fantasy.player.PlayerProfile;

@RestController
public class FixtureController{
	
	@Autowired
	FixtureService fixtureService;
	
	@RequestMapping(value = "/api/fixtures", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(View.PublicGeneral.class)
	public ResponseEntity getFixtures(
										@RequestParam("fixtureCode") Optional<Integer> fixtureCode,
										@RequestParam("round") Optional<Integer> round,
										@RequestParam("team") Optional<String> team
									) throws JSONException {
		if (!fixtureCode.isPresent() && !round.isPresent() && !team.isPresent()) {
			return ResponseEntity.badRequest().body("Either fixtureCode or round or team Query Parameter is required");
		}
		List<Fixture> fixtures = fixtureService.getFixtures();
		if (fixtureCode.isPresent()) {
			fixtures.removeAll(fixtures.stream().filter(x -> x.getCode() != fixtureCode.get()).collect(Collectors.toList()));
		}
		if (round.isPresent()) {
			fixtures.removeAll(fixtures.stream().filter(x -> round.get() != x.getEvent()).collect(Collectors.toList()));
		}
		if (team.isPresent()) {
			fixtures.removeAll(fixtures.stream().filter(x -> !(team.get().equalsIgnoreCase(x.getHomeTeam().getName())
					|| team.get().equalsIgnoreCase(x.getAwayTeam().getName())))
					.collect(Collectors.toList()));
		}
		return ResponseEntity.ok(fixtures);
	}
	
	@RequestMapping(value = "/api/fixtures/{fixtureCode}", method = RequestMethod.GET)
	@JsonView(View.PublicDetails.class)
	public ResponseEntity lineup(@PathVariable("fixtureCode") int fixtureCode, @RequestParam(value = "method", required = false) String method) throws JSONException, JsonParseException, JsonMappingException, IOException {
		try {
			return ResponseEntity.ok(fixtureService.getFixtureByCode(fixtureCode));
		}
		catch (NoSuchElementException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fixture not found.");
		}
	}
}
