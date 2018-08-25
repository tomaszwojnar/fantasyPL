package com.twojnar.fantasy.player.predictions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;

@Component
public class PredictionService {
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	
	public Prediction makePrediction(Player player, AbstractPredictionMethod predictionMethod) {
		return null;
	}
	
	public List<Prediction> makePredictions(Player player, int numberOfGames, AbstractPredictionMethod predictionMethod) {
		List<Prediction> predictions = new ArrayList<Prediction>();
		List<Fixture> fixtures = fixtureService.getNextFixturesForTeam(player.getPlayerProfile().getTeam(), numberOfGames, fantasyStatus.getCurrentSeason());
		fixtures.stream().forEach(x -> {
			double points = predictionMethod.makePrediction(player, x);
			Prediction prediction = new Prediction(
						player.getPlayerProfile().getCode(),
						x.getCode(),
						predictionMethod.getClass().getSimpleName(),
						x.getEvent(),
						points);
						predictions.add(prediction);
		});
		return predictions;
	}
	
}