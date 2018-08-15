package com.twojnar.fantasy.player.predictions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;

@Component
public class PredictionService {
	
	@Autowired
	SimpleRegressionPastSeason simpleRegressionPastSeason;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	
	public Prediction makePrediction(Player player, AbstractPredictionMethod predictionMethod) {
		return null;
	}
	
	public List<Prediction> makePredictions(Player player, int number) {
		List<Prediction> predictions = new ArrayList<Prediction>();
		List<Fixture> fixtures = fixtureService.getNextFixturesForTeam(player.getPlayerProfile().getTeam(), number);
		fixtures.stream().forEach(x -> {
			predictions.add(new Prediction(player, simpleRegressionPastSeason, x));
		});
		return predictions;
	}
	
	

}
