package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.predictions.AbstractPredictionMethod;
import com.twojnar.fantasy.player.predictions.Prediction;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.player.predictions.SimpleRegressionPastSeason;
import com.twojnar.fantasy.team.TeamService;

@Service
@EnableMongoRepositories(basePackageClasses = PlayerRepository.class)
public class PlayerService {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private PredictionService predictionService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private FantasyStatus fantasyStatus;
	
	List<Player> players = new ArrayList<Player>();
	
	public void updateFromDB() {
		this.players = playerRepository.findAll();
	}
	
	public void initialLoad(List<PlayerProfile> playerProfiles) {
		playerRepository.deleteAll();
		for (PlayerProfile newProfile : playerProfiles) {
				this.addPlayer(newProfile);
		};
		this.savePlayers();
	}
	
	public void addPlayer(PlayerProfile playerProfile) {
		Player newPlayer = new Player();
		newPlayer.setPlayerProfile(playerProfile);
		newPlayer.getPlayerProfile().setTeam(teamService.getTeamByFantasyId2018(newPlayer.getPlayerProfile().getTeam().getFantasyId2018()));
		this.players.add(newPlayer);
	}
	
	public void savePlayers() {
		playerRepository.saveAll(this.players);
	}
	
	public void savePlayer(Player player) {
		playerRepository.save(player);
	}
	
	public void updateProfile(PlayerProfile profile) {
		Optional<Player> optionalPlayer = this.players.stream()
				.filter(e -> e.getPlayerProfile().getCode() == profile.getCode()).findFirst();
		if (optionalPlayer.isPresent()) {
			Player oldPlayer = optionalPlayer.get();
			profile.setFantasyId2017(oldPlayer.getPlayerProfile().getFantasyId2017());
			oldPlayer.setPlayerProfile(profile);
		}
		else this.addPlayer(profile);
	}
	
	public void updateHistorySeasons(int code, List<HistorySeason> historySeasons) {
		/*this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == code)
		.findFirst()
		.ifPresent(x -> {
			historySeasons.stream()
						  .filter(y -> y.getSeasonName().equalsIgnoreCase("2017/18"))
						  .findFirst()
						  .ifPresent(y -> y.setTeam(x.getHistorySeasons()
								  .stream()
								  .filter(z -> z.getSeasonName().equalsIgnoreCase("2017/18"))
								  .findFirst()
								  .get().getTeam()
								  ));*/
		
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == code).findFirst().ifPresent(x -> {
			x.setHistorySeasons(historySeasons);
		});
	}
	
	public void updatePerformances(int playerCode, List<FullPerformance> performances, Boolean retainExistingPredictions) {
		Player player = this.getPlayerByCode(playerCode);
		List<FullPerformance> existingPerformances = player.getPerformances();
		List<FullPerformance> futurePerformances = existingPerformances.stream().filter(x -> x.getRound()>fantasyStatus.getCurrentEvent()).collect(Collectors.toList());
		performances.stream().forEach(x -> this.completeFixtureData(x, "2018/19"));
		performances.stream()
			.filter(x -> this.performanceExists(existingPerformances, x))
			.forEach(x -> x.setPredictions(this.getCurrentPerformanceByPlayerAndFixtureCode(player, x.getFixture().getCode()).getPredictions()));
		performances.addAll(futurePerformances);
		player.setPerformances(performances);
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	

	
	public void completeFixtureData(FullPerformance fullPerformance, String season) {
		fullPerformance.setFixture(fixtureService.getFixtureByFantasyIdAndSeason(fullPerformance.getFixture().getFantasyId(), season));
	}
	
	public void makePredictions(Player player, int number, AbstractPredictionMethod method) throws Exception {
		
		List<Prediction> predictions = predictionService.makePredictions(player, number, method);
		for (Prediction prediction : predictions) {
			System.out.println(player.getPlayerProfile().getLastName() + prediction.getFixtureCode());
			FullPerformance performance = this.getCurrentPerformanceByPlayerAndFixtureCode(player, prediction.getFixtureCode());
			if (null == performance) performance = this.createNewPerformance(player, prediction.getFixtureCode());
			if ((!this.predictionExists(player, prediction)))
				{
					performance.addPrediction(prediction);
				}
		}
		this.savePlayer(player);
		}

	
	
	
	public void makePredictionsForAllPlayers(int numberOfGames, AbstractPredictionMethod method) {
    	for (Player player : this.getPlayers()) {
    		try {
    			this.makePredictions(player, 10, method);
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
	}
	
	/**
	 * Adds a preditction to an existing player performance. If none exists creates a new performance.
	 * 
	 * 
	 */
	
	public void addPredictionToPlayerPerformance(Prediction prediction, Player player, Fixture fixture) {
		Optional <FullPerformance> optionalPerformance = player.getPerformances()
			  .stream()
			  .filter(x -> x.getRound() == fixture.getEvent())
			  .findFirst();
		if (optionalPerformance.isPresent() && !this.predictionExists(player, prediction)) {
			optionalPerformance.get().addPrediction(prediction);
		}
		else {
			  FullPerformance performance = new FullPerformance();
			  performance.setFixture(fixture);
			  performance.setRound(fixture.getEvent());
			  performance.addPrediction(prediction);
			  player.getPerformances().add(performance);
		}	  		
	}
	
	
	public FullPerformance createNewPerformance (Player player, int fixtureCode) {
			Fixture fixture = fixtureService.getFixtureByCode(fixtureCode);
			FullPerformance performance = new FullPerformance();
			performance.setFixture(fixture);
			performance.setRound(fixture.getEvent());
			player.getPerformances().add(performance);
			return performance;
	}
	
	public List<FullPerformance> getPerformancesByPlayerAndRound(Player player, int round) {
		return this.getPlayers()
				     .stream()
				     .filter(x -> x.equals(player))
				     .findFirst()
				     .get()
				     .getPerformances()
				     .stream()
				     .filter(y -> y.getRound() == round)
				     .collect(Collectors.toList());
	}
	
	public List<Performance> getPastPerformancesByPlayerRoundAndSeason(Player player, int round, String season) {
		
		return this.getPlayers().stream()
								  .filter(x -> x.equals(player))
								  .findFirst()
								  .get()
								  .getHistorySeasons()
								  .stream()
								  .filter(y -> y.getSeasonName().equals(season))
								  .findFirst()
								  .get()
								  .getHistoryPerformances()
								  .stream()
								  .filter(y -> y.getRound() == round)
								  .collect(Collectors.toList());
		}
	
	public Player getPlayerByCode(int code) {
		return this.getPlayers().stream().filter(x -> x.getPlayerProfile().getCode() == code).findFirst().get();
	}
	
	public Player getPlayerByFantasyIdAndSeason(int id, String season) {
			switch (season) {
			case "2018/19" :
				return this.getPlayers().stream().filter(x -> x.getPlayerProfile().getFantasyId2018() == id).findFirst().get();
			case "2017/18" :
				return this.getPlayers().stream().filter(x -> x.getPlayerProfile().getFantasyId2017() == id).findFirst().get();
			default :
				return null;
			}
	}
	
	public HistorySeason getHistorySeason(Player player, String season) {
		Optional<HistorySeason> historySeason = player.getHistorySeasons().stream().filter(x -> x.getSeasonName().equalsIgnoreCase(season)).findFirst();
		if (historySeason.isPresent()) return historySeason.get();
		else return null;
	}
	
	public Boolean performanceExists(List<FullPerformance> haystack, Performance needle) {
		return haystack.stream().filter(x -> x.getRound() == needle.getRound()).findFirst().isPresent();

	}
	
	public Boolean predictionExists(Player player, Prediction prediction) {
		for (FullPerformance performance : getPerformancesByPlayerAndRound(player, prediction.getRound())) {
			if (performance
				.getPredictions()
				.stream()
				.filter(x ->x.getFixtureCode() == prediction.getFixtureCode() &&
							x.getPredictedPoints().equals(prediction.getPredictedPoints()) && 
							x.getPredictionMethodName().equalsIgnoreCase(prediction.getPredictionMethodName())
						)
				.findFirst()
				.isPresent()) return true;
		}
		return false;
	}
	
	public Boolean isPlayerInFixture(Player player, Fixture fixture) {
		return player.getPlayerProfile().getTeam().equals(fixture.getAwayTeam()) || player.getPlayerProfile().getTeam().equals(fixture.getHomeTeam()) ||
				player.getHistorySeasons().stream().filter(x -> x.getHistoryPerformances().stream().filter(y -> y.getFixture().equals(fixture)).findFirst().isPresent()).findFirst().isPresent();
	}
	
	public double getPredictedPointsForNextXGames(Player player, int noOfGames) {
		double points = 0;
		int currentEvent = fantasyStatus.getCurrentEvent();
		for (int i = 1; i <= noOfGames; i++) {
			if (currentEvent + i > 38) break;
			for (FullPerformance performanceInRound : this.getPerformancesByPlayerAndRound(player, currentEvent + i)) {
				points += performanceInRound.getAveragePrediction();
			}
		}
		return points;
	}
	
	public double getPredictedPointsForNextXGamesUsingMethod(Player player, int noOfGames, String method) {
		double points = 0;
		int currentEvent = fantasyStatus.getCurrentEvent();
		for (int i = 1; i <= noOfGames; i++) {
			if (currentEvent + i > 38) break;
			for (FullPerformance performanceInRound : this.getPerformancesByPlayerAndRound(player, currentEvent + i)) {
				points += performanceInRound.getLatestPredictionByMethod(method).getPredictedPoints();
			}
		}
		return points;
	}
	
	public FullPerformance getCurrentPerformanceByPlayerAndFixtureCode(Player player, int fixtureCode) {
		Optional<FullPerformance> optionalPerformance = player.getPerformances().stream().filter(x -> fixtureCode == x.getFixture().getCode()).findFirst();
		if (optionalPerformance.isPresent()) return optionalPerformance.get();
		else return null;
	}
}
	

