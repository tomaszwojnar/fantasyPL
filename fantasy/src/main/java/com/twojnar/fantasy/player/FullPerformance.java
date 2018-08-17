package com.twojnar.fantasy.player;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twojnar.fantasy.fixture.Fixture;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPerformance extends Performance {
	
	private Fixture fixture;
	
	private Map<String, String> explain;
	
	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Map<String, String> getExplain() {
		return explain;
	}

	public void setExplain(Map<String, String> explain) {
		this.explain = explain;
	}

}



