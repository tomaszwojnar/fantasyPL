package com.twojnar.scrapper;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;


public class FantasyTeamResponse implements IResponse {

	@Override
	public List<Team> getObjectsFromString(String string) throws JsonParseException, JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();
	JavaType listType = mapper
		    .getTypeFactory()
		    .constructCollectionType(List.class, Team.class);
	return mapper.readValue(string, listType);
	}
}
