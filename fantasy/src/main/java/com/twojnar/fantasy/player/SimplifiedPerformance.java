package com.twojnar.fantasy.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;


public class SimplifiedPerformance extends Performance {
	
	@Autowired
	TeamService teamService;
	
	private String opponentTeam;
	
	private int opponentStrength;
	
	public String getOpponentTeam() {
		return opponentTeam;
	}

	public void setOpponentTeam(String opponentTeam) {
		this.opponentTeam = opponentTeam;
	}

	public int getOpponentStrength() {
		return opponentStrength;
	}

	public void setOpponentStrength(int opponentStrength) {
		this.opponentStrength = opponentStrength;
	}


	
	

}
