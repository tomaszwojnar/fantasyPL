package com.twojnar.taskRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.predictions.Prediction;

@Component
public class DataCleanDefinition implements TaskDefinition {
	
	@Autowired
	PlayerService playerService;
	
	
	public void cleanPredictions() {
		for (Player player : playerService.getPlayers()) {
			for (FullPerformance performance : player.getPerformances()) {
				List<Prediction> latestPredictions = playerService.getLatestPredictionPerMethod(performance);
				performance.setPredictions(latestPredictions);
			}
			playerService.savePlayer(player);
		}

	}
	
	public void clean5thRound() {
		for (Player player : playerService.getPlayers()) {
			List<FullPerformance> performances = player.getPerformances();
			List<FullPerformance> fifthRound = performances.stream().filter(x -> x.getRound() == 5).collect(Collectors.toList());
	    	for (FullPerformance fifthRoundPerformance : fifthRound) {
	    		if (fifthRoundPerformance.getPredictions().size() < 2) player.getPerformances().remove(fifthRoundPerformance);
	    	}
			playerService.savePlayer(player);
		}
	}

	@Override
	public void updateAll() throws IOException {
		// TODO Auto-generated method stub
	}


	@Override
	public void initialLoad() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
