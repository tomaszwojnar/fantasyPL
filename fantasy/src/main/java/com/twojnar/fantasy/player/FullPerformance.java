package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.player.predictions.Prediction;
import com.twojnar.fantasy.team.Team;

@JsonView(View.PublicGeneral.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPerformance {
	
	@JsonView(View.PublicDetails.class)
	List<Prediction> predictions = new ArrayList<Prediction>();
	
	private Fixture fixture;
	
	private Map<String, String> explain;

	@JsonProperty("team_h_score")
	private int teamHomeScore;
	
	@JsonProperty("team_a_score")
	private int teamAwayScore;
	
	@JsonProperty("was_home")
	private Boolean wasHome;
	
	@JsonProperty("round")
	private int round;
	
	@JsonProperty("total_points")
	private int totalPoints;
	
	@JsonProperty("value")
	private int value;
	
	private int selected;
	
	@JsonProperty("transfers_in")
	private int transfersIn;
	
	@JsonProperty("transfers_out")
	private int transfersOut;
	
	@JsonProperty("transfers_balance")
	private int transfersBalance;
	
	@JsonProperty("loaned_in")
	private int loanedIn;
	
	@JsonProperty("loaned_out")
	private int loanedOut;
	
	private int minutes;
	
	@JsonProperty("goals_scored")
	private int goalsScored;
	
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
	private int bps;
	
	@JsonProperty("influence")
	private float influence;

	@JsonProperty("creativity")
	private float creativity;
	
	@JsonProperty("threat")
	private float threat;
	
	@JsonProperty("ict_index")
	private float ictIndex;
	
	@JsonProperty("ea_index")
	private int eaIndex;
	
	@JsonProperty("open_play_crosses")
	private int openPlayCrosses;
	
	@JsonProperty("big_chances_created")
	private int bigChancesCreated;
	
	@JsonProperty("clearances_blocks_interceptions")
	private int clearancesBlocksInterceptions;
	
	@JsonProperty("recoveries")
	private int recoveries;
	
	@JsonProperty("key_passes")
	private int keyPasses;
	
	@JsonProperty("tackles")
	private int tackles;
	
	@JsonProperty("winning_goals")
	private int winningGoals;

	@JsonProperty("attempted_passes")
	private int attemptedPasses;

	@JsonProperty("completed_passes")
	private int completedPasses;

	@JsonProperty("penalties_conceded")
	private int penaltiesConceded;
	
	@JsonProperty("big_chances_missed")
	private int bigChancesMissed;
	
	@JsonProperty("errors_leading_to_goal")
	private int errorsLeadingToGoal;
	
	@JsonProperty("errors_leading_to_goal_attempt")
	private int errorsLeadingToGoalAttempt;

	private int tackled;
	
	private int offside;
	
	@JsonProperty("target_missed")
	private int targetMissed;
	
	private int fouls;
	
	private int dribbles;


	public int getTeamHomeScore() {
		return teamHomeScore;
	}

	public void setTeamHomeScore(int teamHomeScore) {
		this.teamHomeScore = teamHomeScore;
	}

	public int getTeamAwayScore() {
		return teamAwayScore;
	}

	public void setTeamAwayScore(int teamAwayScore) {
		this.teamAwayScore = teamAwayScore;
	}

	public Boolean getWasHome() {
		return wasHome;
	}

	public void setWasHome(Boolean wasHome) {
		this.wasHome = wasHome;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public int getTransfersIn() {
		return transfersIn;
	}

	public void setTransfersIn(int transfersIn) {
		this.transfersIn = transfersIn;
	}

	public int getTransfersOut() {
		return transfersOut;
	}

	public void setTransfersOut(int transfersOut) {
		this.transfersOut = transfersOut;
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

	public int getBps() {
		return bps;
	}

	public void setBps(int bps) {
		this.bps = bps;
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

	public int getEaIndex() {
		return eaIndex;
	}

	public void setEaIndex(int eaIndex) {
		this.eaIndex = eaIndex;
	}

	public int getOpenPlayCrosses() {
		return openPlayCrosses;
	}

	public void setOpenPlayCrosses(int openPlayCrosses) {
		this.openPlayCrosses = openPlayCrosses;
	}

	public int getBigChancesCreated() {
		return bigChancesCreated;
	}

	public void setBigChancesCreated(int bigChancesCreated) {
		this.bigChancesCreated = bigChancesCreated;
	}

	public int getClearancesBlocksInterceptions() {
		return clearancesBlocksInterceptions;
	}

	public void setClearancesBlocksInterceptions(int clearancesBlocksInterceptions) {
		this.clearancesBlocksInterceptions = clearancesBlocksInterceptions;
	}

	public int getRecoveries() {
		return recoveries;
	}

	public void setRecoveries(int recoveries) {
		this.recoveries = recoveries;
	}

	public int getKeyPasses() {
		return keyPasses;
	}

	public void setKeyPasses(int keyPasses) {
		this.keyPasses = keyPasses;
	}

	public int getTackles() {
		return tackles;
	}

	public void setTackles(int tackles) {
		this.tackles = tackles;
	}

	public int getWinningGoals() {
		return winningGoals;
	}

	public void setWinningGoals(int winningGoals) {
		this.winningGoals = winningGoals;
	}

	public int getAttemptedPasses() {
		return attemptedPasses;
	}

	public void setAttemptedPasses(int attemptedPasses) {
		this.attemptedPasses = attemptedPasses;
	}

	public int getCompletedPasses() {
		return completedPasses;
	}

	public void setCompletedPasses(int completedPasses) {
		this.completedPasses = completedPasses;
	}

	public int getPenaltiesConceded() {
		return penaltiesConceded;
	}

	public void setPenaltiesConceded(int penaltiesConceded) {
		this.penaltiesConceded = penaltiesConceded;
	}

	public int getBigChancesMissed() {
		return bigChancesMissed;
	}

	public void setBigChancesMissed(int bigChancesMissed) {
		this.bigChancesMissed = bigChancesMissed;
	}

	public int getErrorsLeadingToGoal() {
		return errorsLeadingToGoal;
	}

	public void setErrorsLeadingToGoal(int errorsLeadingToGoal) {
		this.errorsLeadingToGoal = errorsLeadingToGoal;
	}

	public int getErrorsLeadingToGoalAttempt() {
		return errorsLeadingToGoalAttempt;
	}

	public void setErrorsLeadingToGoalAttempt(int errorsLeadingToGoalAttempt) {
		this.errorsLeadingToGoalAttempt = errorsLeadingToGoalAttempt;
	}

	public int getTackled() {
		return tackled;
	}

	public void setTackled(int tackled) {
		this.tackled = tackled;
	}

	public int getOffside() {
		return offside;
	}

	public void setOffside(int offside) {
		this.offside = offside;
	}

	public int getTargetMissed() {
		return targetMissed;
	}

	public void setTargetMissed(int targetMissed) {
		this.targetMissed = targetMissed;
	}

	public int getFouls() {
		return fouls;
	}

	public void setFouls(int fouls) {
		this.fouls = fouls;
	}

	public int getDribbles() {
		return dribbles;
	}

	public void setDribbles(int dribbles) {
		this.dribbles = dribbles;
	}

	public List<Prediction> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<Prediction> predictions) {
		this.predictions = predictions;
	}

	public int getTransfersBalance() {
		return transfersBalance;
	}

	public void setTransfersBalance(int transfersBalance) {
		this.transfersBalance = transfersBalance;
	}
	
	public void addPrediction(Prediction prediction) {
		this.predictions.add(prediction);
	}
	
	@JsonIgnore
	public Prediction getLatestPredictionByMethod(String method) {
		if (this.predictions.size() == 0) return null;
		if (this.predictions.stream().filter(x -> x.getPredictionMethodName().equalsIgnoreCase(method)).findAny().isPresent())
		{
			return this.predictions.stream().filter(x -> x.getPredictionMethodName().equalsIgnoreCase(method)).max(Comparator.comparing(Prediction::getDatePredictionMade, Comparator.nullsLast(Comparator.reverseOrder()))).get();
		}
		else return null;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixture == null) ? 0 : fixture.hashCode());
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
		FullPerformance other = (FullPerformance) obj;
		if (fixture == null) {
			if (other.fixture != null)
				return false;
		} else if (!fixture.equals(other.fixture))
			return false;
		return true;
	}
	
	public boolean isHome(Team team) {
		return this.getFixture().getHomeTeam().equals(team);
	}
}



