package com.twojnar.fantasy.player;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistorySeason {
	
	@JsonProperty("id")
	private int fantasyId;
	
	@JsonProperty("season_name")
	private String seasonName;
	
	@JsonProperty("start_cost")
	private int	startCost;
	
	@JsonProperty("end_cost")
	private int endCost;
	
	@JsonProperty("total_points")
	private int totalPoints;
	
	private int minutes;
	
	@JsonProperty("clean_sheets")
	private int cleanSheets;
	
	@JsonProperty("goals_conceded")
	private int goalsConceded;
	
	@JsonProperty("own_goals")
	private int ownGoals;
	
	@JsonProperty("penalties_saved")
	private int penalitiesSaved;
	
	@JsonProperty("penalties_missed")
	private int penaltiesMissed;
	
	@JsonProperty("yellow_cards")
	private int yellowCards;
	
	@JsonProperty("red_cards")
	private int redCards;
	
	private int saves;
	
	private int assists;
	
	private int bonus;
	
	private int bps;
	
	private float influence;
	
	private float creativity;
	
	private float threat;
	
	@JsonProperty("ict_index")
	private float ictIndex;
	
	@JsonProperty("ea_index")
	private int eaIndex;
	
	private int season;

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public int getStartCost() {
		return startCost;
	}

	public void setStartCost(int startCost) {
		this.startCost = startCost;
	}

	public int getEndCost() {
		return endCost;
	}

	public void setEndCost(int endCost) {
		this.endCost = endCost;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getCleanSheets() {
		return cleanSheets;
	}

	public void setCleanSheets(int cleanSheets) {
		this.cleanSheets = cleanSheets;
	}

	public int getGoalsConceded() {
		return goalsConceded;
	}

	public void setGoalsConceded(int goalsConceded) {
		this.goalsConceded = goalsConceded;
	}

	public int getOwnGoals() {
		return ownGoals;
	}

	public void setOwnGoals(int ownGoals) {
		this.ownGoals = ownGoals;
	}

	public int getPenalitiesSaved() {
		return penalitiesSaved;
	}

	public void setPenalitiesSaved(int penalitiesSaved) {
		this.penalitiesSaved = penalitiesSaved;
	}

	public int getPenaltiesMissed() {
		return penaltiesMissed;
	}

	public void setPenaltiesMissed(int penaltiesMissed) {
		this.penaltiesMissed = penaltiesMissed;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}

	public int getRedCards() {
		return redCards;
	}

	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}

	public int getSaves() {
		return saves;
	}

	public void setSaves(int saves) {
		this.saves = saves;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public int getBps() {
		return bps;
	}

	public void setBps(int bps) {
		this.bps = bps;
	}

	public float getInfluence() {
		return influence;
	}

	public void setInfluences(float influence) {
		this.influence = influence;
	}

	public float getThreat() {
		return threat;
	}

	public void setThreat(float threat) {
		this.threat = threat;
	}

	public float getIctIndex() {
		return ictIndex;
	}

	public void setIctIndex(float ictIndex) {
		this.ictIndex = ictIndex;
	}

	public int getEaIndex() {
		return eaIndex;
	}

	public void setEaIndex(int eaIndex) {
		this.eaIndex = eaIndex;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public void setInfluence(float influence) {
		this.influence = influence;
	}

	public float getCreativity() {
		return creativity;
	}

	public void setCreativity(float creativity) {
		this.creativity = creativity;
	}
	
	
	
}
