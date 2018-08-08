package com.twojnar.scrapper;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerProfile;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;


@Component
public class FantasyPlayerDetailsResponse implements IResponse {
	
	@Override
	public List<?> getObjectsFromString(String string) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JavaType listType = mapper
			    .getTypeFactory()
			    .constructCollectionType(List.class, HistorySeason.class);
		return mapper.readValue(string, listType);
	}
}
