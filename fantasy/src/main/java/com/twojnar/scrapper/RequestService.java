package com.twojnar.scrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.twojnar.fantasy.common.FantasyStatus;

@Service
public class RequestService {
	
	private RestTemplate restTemplate;
	
	static final Logger logger = LoggerFactory.getLogger(RequestService.class);
	
	@Autowired
	public RequestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
          }

	public String sendRequest(Request request) {
		logger.info("Sending request :" + request.getUri());
		return request.send();
	}
	
	public Request defineRequest(String uri) {
		return new Request(uri, restTemplate);
	}
	

}
