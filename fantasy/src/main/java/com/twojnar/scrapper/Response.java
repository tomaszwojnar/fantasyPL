package com.twojnar.scrapper;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Response implements IResponse {

	@Override
	public List<?> getListFromString(String string, Object object) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		JavaType listType = mapper
			    .getTypeFactory()
			    .constructCollectionType(List.class, object.getClass());
		return mapper.readValue(string, listType);
	}
	
	@Override
	public Object getObjectFromString(String string, Object object) throws JsonParseException, JsonMappingException, IOException {
		return object;
	}
}
