package com.twojnar.scrapper;

import java.io.IOException;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.team.Team;

@Component
public class FantasyStatusResponse implements IResponse {

	@Override
	public List<Team> getObjectsFromString(String string) throws JsonParseException, JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();
	mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	JavaType listType = mapper
		    .getTypeFactory()
		    .constructCollectionType(List.class, FantasyStatus.class);
	return mapper.readValue(string, listType);
	}
}