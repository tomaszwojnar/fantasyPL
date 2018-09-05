package com.twojnar.scrapper;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class Request {
	
	private String uri;
	
	private RestTemplate restTemplate;
	
	private HttpMethod method;
	
	private Map<String, String> queryParameters;
	
	private HttpEntity<?> requestEntity;
	
	
	public Request(String uri, RestTemplate restTemplate) {
		super();
		this.uri = uri;
		this.restTemplate = restTemplate;
		this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		requestHeaders.setAccept(mediaTypes);
		requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		this.requestEntity = new HttpEntity<Object>(requestHeaders);
		this.method = HttpMethod.GET;
		
	}

	public String send() {
		
		String response = restTemplate.exchange(this.uri, this.method, this.requestEntity, String.class).getBody();
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
