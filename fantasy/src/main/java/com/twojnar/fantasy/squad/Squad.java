package com.twojnar.fantasy.squad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Squad {
	
	private String id;
	
	private int fantasyId;
	
	@JsonProperty("overall_points")
	private int overallPoints;

	private int value;
	
	private int bank;
	
	private List<Lineup> lineups;

}
