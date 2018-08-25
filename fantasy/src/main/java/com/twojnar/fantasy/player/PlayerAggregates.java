package com.twojnar.fantasy.player;

public class PlayerAggregates {
	
	private String season;
	
	private double overallDiffPointsCorrelation;
	
	private double attackDefencePointsCorrelation;
	
	private double overallPointsCorrelationFromSimilarMatches;

	public double getOverallDiffPointsCorrelation() {
		return overallDiffPointsCorrelation;
	}

	public void setOverallDiffPointsCorrelation(double overallDiffPointsCorrelation) {
		this.overallDiffPointsCorrelation = overallDiffPointsCorrelation;
	}

	public double getAttackDefencePointsCorrelation() {
		return attackDefencePointsCorrelation;
	}

	public void setAttackDefencePointsCorrelation(double attackDefencePointsCorrelation) {
		this.attackDefencePointsCorrelation = attackDefencePointsCorrelation;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public double getOverallPointsCorrelationFromSimilarMatches() {
		return overallPointsCorrelationFromSimilarMatches;
	}

	public void setOverallPointsCorrelationFromSimilarMatches(double overallPointsCorrelationFromSimilarMatches) {
		this.overallPointsCorrelationFromSimilarMatches = overallPointsCorrelationFromSimilarMatches;
	}
	
	
}
