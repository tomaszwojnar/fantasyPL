package com.twojnar.scrapper;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;


public class Request {
	
	private String uri;
	
	private RestTemplate restTemplate;
	
	private HttpMethod method;
	
	private Map<String, String> queryParameters;
	
	
	public Request(String uri, RestTemplate restTemplate) {
		super();
		this.uri = uri;
		this.restTemplate = restTemplate;
	}

	public String send() {
		
		String response = restTemplate.getForObject(this.uri, String.class);
		return response;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}


	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	
	

	
	

}
