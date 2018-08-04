package com.twojnar.scrapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IResponse {
	
	public List<?> getObjectsFromString(String string) throws JsonParseException, JsonMappingException, IOException;
	
}
