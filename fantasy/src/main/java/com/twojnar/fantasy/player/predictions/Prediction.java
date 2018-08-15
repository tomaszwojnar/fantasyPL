package com.twojnar.fantasy.player.predictions;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Transient;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.player.Player;

public class Prediction {
	
	private Date datePredictionMade;
	
	private AbstractPredictionMethod predictionMethod;
	
	private double predictedPoints;
	
	private Map<String, Object> calculationDetails;
	
	@Transient
	private Player player;
	
	@Transient
	private Fixture fixture;
	
	public Prediction() {
		super();
	}
	
	public Prediction(Player player, AbstractPredictionMethod predictionMethod, Fixture fixture) {
		this.player = player;
		this.predictionMethod = predictionMethod;
		this.fixture = fixture;
		this.predictedPoints = predictionMethod.makePrediction(this.player, this.fixture);
		this.datePredictionMade = new Date();
	}

	public Date getDatePredicitionMade() {
		return datePredictionMade;
	}

	public void setDatePredicitionMade(Date datePredicitionMade) {
		this.datePredictionMade = datePredicitionMade;
	}

	public AbstractPredictionMethod getPredictionMethod() {
		return predictionMethod;
	}

	public void setPredictionMethod(AbstractPredictionMethod predictionMethod) {
		this.predictionMethod = predictionMethod;
	}

	public double getPredictedPoints() {
		return predictedPoints;
	}

	public void setPredictedPoints(double predictedPoints) {
		this.predictedPoints = predictedPoints;
	}

	public Map<String, Object> getCalculationDetails() {
		return calculationDetails;
	}

	public void setCalculationDetails(Map<String, Object> calculationDetails) {
		this.calculationDetails = calculationDetails;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
	
	
}
