package com.twojnar.fantasy.player;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPerformance extends Performance {
	
	private Fixture fixture;
	
	private Map<String, String> explain;
	
	@JsonProperty("opponent_team")
	private Team opponentTeam;

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

	public Team getOpponentTeam() {
		return opponentTeam;
	}

	public void setOpponentTeam(Team opponentTeam) {
		this.opponentTeam = opponentTeam;
	}
}



