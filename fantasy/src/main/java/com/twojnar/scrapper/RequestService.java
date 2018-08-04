package com.twojnar.scrapper;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {
	
	
	private RestTemplate restTemplate;
	
	public RequestService() {
		this.restTemplate = new RestTemplate();

	}

	public String sendRequest(Request request) {
		return request.send();
	}
	
	public Request defineRequest(String uri) {
		return new Request(uri, restTemplate);
	}
	

}
