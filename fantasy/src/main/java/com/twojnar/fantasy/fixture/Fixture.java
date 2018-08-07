package com.twojnar.fantasy.fixture;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojnar.fantasy.team.Team;


@Document(collection = "fixtures")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fixture {
	
	@JsonIgnore
	private String id;
	
	@JsonProperty("id")
	private int fantasyId;
	
	@JsonProperty("deadline_time")
	private Date deadlineTime;
	
	@JsonProperty("kickoff_time")
	private Date kickoffTime;
	
	//private FixtureStats stats;
	
	@JsonProperty("team_h")
	private Team homeTeam;
	
	@JsonProperty("team_a")
	private Team awayTeam;

	@JsonProperty("team_h_difficulty")
	private int homeTeamDifficulty;
	
	@JsonProperty("team_a_difficulty")
	private int awayTeamDifficulty;
	
	private int code;
	
	@JsonProperty("team_h_score")
	private int homeTeamScore;
	
	@JsonProperty("team_a_score")
	private int awayTeamScore;
	
	private boolean finished;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFantasyId() {
		return fantasyId;
	}

	public void setFantasyId(int fantasyId) {
		this.fantasyId = fantasyId;
	}

	public Date getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public Date getKickoffTime() {
		return kickoffTime;
	}

	public void setKickoffTime(Date kickoffTime) {
		this.kickoffTime = kickoffTime;
	}

	//public FixtureStats getStats() {
	//	return stats;
	//}

	//public void setStats(FixtureStats stats) {
	//	this.stats = stats;
	//}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getHomeTeamDifficulty() {
		return homeTeamDifficulty;
	}

	public void setHomeTeamDifficulty(int homeTeamDifficulty) {
		this.homeTeamDifficulty = homeTeamDifficulty;
	}

	public int getAwayTeamDifficulty() {
		return awayTeamDifficulty;
	}

	public void setAwayTeamDifficulty(int awayTeamDifficulty) {
		this.awayTeamDifficulty = awayTeamDifficulty;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasyId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fixture other = (Fixture) obj;
		if (fantasyId != other.fantasyId)
			return false;
		return true;
	}

}
