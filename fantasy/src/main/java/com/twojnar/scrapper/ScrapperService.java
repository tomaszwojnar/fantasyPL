package com.twojnar.scrapper;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class ScrapperService {
	
	@Autowired
	RequestService requestService;
	
	public List<?> scrapAll(String uri, IResponse response) throws JsonParseException, JsonMappingException, IOException {
		String JSONResponse =  requestService.sendRequest(requestService.defineRequest(uri));
		return response.getObjectsFromString(JSONResponse);
	}
	
	public List<?> scrapField(String uri, String field, IResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return response.getObjectsFromString(new JSONObject(requestService.sendRequest(requestService.defineRequest(uri))).getString(field));
	}
}
