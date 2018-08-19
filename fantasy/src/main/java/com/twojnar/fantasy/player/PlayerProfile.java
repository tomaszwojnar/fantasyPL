package com.twojnar.fantasy.player;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojnar.fantasy.team.Team;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerProfile {
	
	@JsonProperty("id")
	private int fantasyId2018;
	
	@JsonIgnore
	private int fantasyId2017;
	
	@JsonProperty("first_name")
	private String firstName;
	
	@JsonProperty("second_name")
	private String lastName;
	
	private String photo;
	
	@JsonProperty("team")
	private Team team;
	
	private int code;
	
	@JsonProperty("now_cost")
	private int	nowCost;
	
	@JsonProperty("news_added")
	private String newAdded;
	
	@JsonProperty("chance_of_playing_this_round")
	private String chanceOfPlayingThisRound;
	
	@JsonProperty("chance_of_playing_next_round")
	private String chanceOfPlayingNextRound;
	
	@JsonProperty("value_form")
	private float valueForm;
	
	@JsonProperty("value_season")
	private float valueSeason;
	
	@JsonProperty("cost_change_start")
	private int costChangeStart;
	
	@JsonProperty("cost_change_event")
	private int costChangeEvent;
	
	@JsonProperty("cost_change_start_fall")
	private int costChangeStartFall;
	
	@JsonProperty("cost_change_event_fall")
	private int costChangeEventFall;
	
	@JsonProperty("in_dreamteam")
	private Boolean inDreamteam;
	
	@JsonProperty("dreamteam_count")
	private int dreamteamCount;
	
	@JsonProperty("selected_by_percent")
	private float selectedByPercent;
	
	@JsonProperty("form")
	private float form;
	
	@JsonProperty("transfers_out")
	private int transfersOut;
	
	@JsonProperty("transfers_in")
	private int transfersIn;
	
	@JsonProperty("transfers_out_event")
	private int transfersOutEvent;
	
	@JsonProperty("transfers_in_event")
	private int transfersInEvent;
	
	@JsonProperty("loans_in")
	private int loansIn;
	
	@JsonProperty("loans_out")
	private int loansOut;
	
	@JsonProperty("loaned_in")
	private int loanedIn;
	
	@JsonProperty("loaned_out")
	private int loanedOut;
	
	@JsonProperty("total_points")
	private int totalPoints;
	
	@JsonProperty("event_points")
	private int eventPoints;
	
	@JsonProperty("points_per_game")
	private float pointsPerGame;
	
	@JsonProperty("ep_this")
	private float expectedPointsThisRound;
	
	@JsonProperty("ep_next")
	private float expectedPointsNextRound;
	
	@JsonProperty("special")
	private Boolean special;
	
	@JsonProperty("minutes")
	private int minutes;
	
	@JsonProperty("goals_scored")
	private int goalsScored;
	
	@JsonProperty("assists")
	private int assists;
	
	@JsonProperty("clean_sheets")
	private int cleanSheets;
	
	@JsonProperty("goals_conceded")
	private int goalsConceded;
	
	@JsonProperty("own_goals")
	private int ownGoals;
	
	@JsonProperty("penalties_saved")
	private int penaltiesSaved;
	
	@JsonProperty("penalties_missed")
	private int penaltiesMissed;
	
	@JsonProperty("yellow_cards")
	private int yellowCards;
	
	@JsonProperty("red_cards")
	private int redCards;
	
	@JsonProperty("saves")
	private int saves;
	
	@JsonProperty("bonus")
	private int bonus;
	
	@JsonProperty("bps")
	private int bonusPointsSystemTotal;
	
	@JsonProperty("influence")
	private float influence;
	
	@JsonProperty("creativity")
	private float creativity;
	
	@JsonProperty("threat")
	private float threat;
	
	@JsonProperty("ict_index")
	private float ictIndex;
	
	@JsonProperty("ea_index")
	private float eaIndex;
	
	@JsonProperty("element_type")
	private int position;
	
	public int getFantasyId2018() {
		return fantasyId2018;
	}

	public void setFantasyId2018(int fantasyId2018) {
		this.fantasyId2018 = fantasyId2018;
	}
	
	public int getFantasyId2017() {
		return fantasyId2017;
	}

	public void setFantasyId2017(int fantasyId2017) {
		this.fantasyId2017 = fantasyId2017;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getNowCost() {
		return nowCost;
	}

	public void setNowCost(int nowCost) {
		this.nowCost = nowCost;
	}

	public String getNewAdded() {
		return newAdded;
	}

	public void setNewAdded(String newAdded) {
		this.newAdded = newAdded;
	}

	public String getChanceOfPlayingThisRound() {
		return chanceOfPlayingThisRound;
	}

	public void setChanceOfPlayingThisRound(String chanceOfPlayingThisRound) {
		this.chanceOfPlayingThisRound = chanceOfPlayingThisRound;
	}

	public String getChanceOfPlayingNextRound() {
		return chanceOfPlayingNextRound;
	}

	public void setChanceOfPlayingNextRound(String chanceOfPlayingNextRound) {
		this.chanceOfPlayingNextRound = chanceOfPlayingNextRound;
	}

	public float getValueForm() {
		return valueForm;
	}

	public void setValueForm(float valueForm) {
		this.valueForm = valueForm;
	}

	public float getValueSeason() {
		return valueSeason;
	}

	public void setValueSeason(float valueSeason) {
		this.valueSeason = valueSeason;
	}

	public int getCostChangeStart() {
		return costChangeStart;
	}

	public void setCostChangeStart(int costChangeStart) {
		this.costChangeStart = costChangeStart;
	}

	public int getCostChangeEvent() {
		return costChangeEvent;
	}

	public void setCostChangeEvent(int costChangeEvent) {
		this.costChangeEvent = costChangeEvent;
	}

	public int getCostChangeStartFall() {
		return costChangeStartFall;
	}

	public void setCostChangeStartFall(int costChangeStartFall) {
		this.costChangeStartFall = costChangeStartFall;
	}

	public int getCostChangeEventFall() {
		return costChangeEventFall;
	}

	public void setCostChangeEventFall(int costChangeEventFall) {
		this.costChangeEventFall = costChangeEventFall;
	}

	public Boolean getInDreamteam() {
		return inDreamteam;
	}

	public void setInDreamteam(Boolean inDreamteam) {
		this.inDreamteam = inDreamteam;
	}

	public int getDreamteamCount() {
		return dreamteamCount;
	}

	public void setDreamteamCount(int dreamteamCount) {
		this.dreamteamCount = dreamteamCount;
	}

	public float getSelectedByPercent() {
		return selectedByPercent;
	}

	public void setSelectedByPercent(float selectedByPercent) {
		this.selectedByPercent = selectedByPercent;
	}

	public float getForm() {
		return form;
	}

	public void setForm(float form) {
		this.form = form;
	}

	public int getTransfersOut() {
		return transfersOut;
	}

	public void setTransfersOut(int transfersOut) {
		this.transfersOut = transfersOut;
	}

	public int getTransfersIn() {
		return transfersIn;
	}

	public void setTransfersIn(int transfersIn) {
		this.transfersIn = transfersIn;
	}

	public int getTransfersOutEvent() {
		return transfersOutEvent;
	}

	public void setTransfersOutEvent(int transfersOutEvent) {
		this.transfersOutEvent = transfersOutEvent;
	}

	public int getTransfersInEvent() {
		return transfersInEvent;
	}

	public void setTransfersInEvent(int transfersInEvent) {
		this.transfersInEvent = transfersInEvent;
	}

	public int getLoansIn() {
		return loansIn;
	}

	public void setLoansIn(int loansIn) {
		this.loansIn = loansIn;
	}

	public int getLoansOut() {
		return loansOut;
	}

	public void setLoansOut(int loansOut) {
		this.loansOut = loansOut;
	}

	public int getLoanedIn() {
		return loanedIn;
	}

	public void setLoanedIn(int loanedIn) {
		this.loanedIn = loanedIn;
	}

	public int getLoanedOut() {
		return loanedOut;
	}

	public void setLoanedOut(int loanedOut) {
		this.loanedOut = loanedOut;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getEventPoints() {
		return eventPoints;
	}

	public void setEventPoints(int eventPoints) {
		this.eventPoints = eventPoints;
	}

	public float getPointsPerGame() {
		return pointsPerGame;
	}

	public void setPointsPerGame(float pointsPerGame) {
		this.pointsPerGame = pointsPerGame;
	}

	public float getExpectedPointsThisRound() {
		return expectedPointsThisRound;
	}

	public void setExpectedPointsThisRound(float expectedPointsThisRound) {
		this.expectedPointsThisRound = expectedPointsThisRound;
	}

	public float getExpectedPointsNextRound() {
		return expectedPointsNextRound;
	}

	public void setExpectedPointsNextRound(float expectedPointsNextRound) {
		this.expectedPointsNextRound = expectedPointsNextRound;
	}

	public Boolean getSpecial() {
		return special;
	}

	public void setSpecial(Boolean special) {
		this.special = special;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
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

	public int getPenaltiesSaved() {
		return penaltiesSaved;
	}

	public void setPenaltiesSaved(int penaltiesSaved) {
		this.penaltiesSaved = penaltiesSaved;
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

	public int getBonusPointsSystemTotal() {
		return bonusPointsSystemTotal;
	}

	public void setBonusPointsSystemTotal(int bonusPointsSystemTotal) {
		this.bonusPointsSystemTotal = bonusPointsSystemTotal;
	}

	public float getInfluence() {
		return influence;
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

	public float getEaIndex() {
		return eaIndex;
	}

	public void setEaIndex(float eaIndex) {
		this.eaIndex = eaIndex;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
	
	

}
