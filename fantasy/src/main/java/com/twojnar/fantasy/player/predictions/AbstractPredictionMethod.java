package com.twojnar.fantasy.player.predictions;

import java.util.Map;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.player.Player;

public abstract class AbstractPredictionMethod implements IPredictionMethod {
	
	public void gatherData() {

	}
	
	public double makePrediction(Player player, Fixture fixture) {
		return 0;
	}
	
	private Map<String, Object> calculationDetails;

	public Map<String, Object> getCalculationDetails() {
		return calculationDetails;
	}

	public void setCalculationDetails(Map<String, Object> calculationDetails) {
		this.calculationDetails = calculationDetails;
	}

}
