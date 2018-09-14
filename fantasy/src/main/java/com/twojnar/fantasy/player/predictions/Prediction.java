package com.twojnar.fantasy.player.predictions;

import java.util.Date;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;


@JsonView(View.PublicGeneral.class)
public class Prediction {
	
	private Date datePredictionMade;
	
	private String predictionMethodName;
	
	private Double predictedPoints;
	
	private int playerCode;
	
	private int fixtureCode;
	
	private int round;
	
	private Map<String, Object> calculationDetails;
	
	public Prediction() {
		super();
	}
	
	public Prediction(int playerCode, int fixtureCode, String predictionMethodName, int round, double predictedPoints) {
		this.fixtureCode = fixtureCode;
		this.playerCode = playerCode;
		this.predictionMethodName = predictionMethodName;
		this.datePredictionMade = new Date();
		this.predictedPoints = predictedPoints;
		this.round = round;
	}

	public Date getDatePredictionMade() {
		return datePredictionMade;
	}

	public void setDatePredictionMade(Date datePredictionMade) {
		this.datePredictionMade = datePredictionMade;
	}

	public String getPredictionMethodName() {
		return predictionMethodName;
	}

	public void setPredictionMethodName(String predictionMethodName) {
		this.predictionMethodName = predictionMethodName;
	}

	public Double getPredictedPoints() {
		return predictedPoints;
	}

	public void setPredictedPoints(double predictedPoints) {
		this.predictedPoints = predictedPoints;
	}

	public int getPlayerCode() {
		return playerCode;
	}

	public void setPlayerCode(int playerCode) {
		this.playerCode = playerCode;
	}

	public int getFixtureCode() {
		return fixtureCode;
	}

	public void setFixtureCode(int fixtureCode) {
		this.fixtureCode = fixtureCode;
	}

	public Map<String, Object> getCalculationDetails() {
		return calculationDetails;
	}

	public void setCalculationDetails(Map<String, Object> calculationDetails) {
		this.calculationDetails = calculationDetails;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}


	
	
}
