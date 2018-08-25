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
	
	@Autowired
	Response response;
	
	public List<?> scrapAll(String uri, Object object) throws JsonParseException, JsonMappingException, IOException {
		String JSONResponse =  requestService.sendRequest(requestService.defineRequest(uri));
		return response.getListFromString(JSONResponse, object);
	}
	
	public List<?> scrapField(String uri, String field, Object object) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return response.getListFromString(new JSONObject(requestService.sendRequest(requestService.defineRequest(uri))).getString(field), object);
	}
}

