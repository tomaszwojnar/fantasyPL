package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.common.ApplicationException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.common.ListUtil;
import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.predictions.AbstractPredictionMethod;
import com.twojnar.fantasy.player.predictions.Prediction;
import com.twojnar.fantasy.player.predictions.PredictionService;
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
	
	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	/**
	 * Updates the players Array using the db connection
	 * @return void
	 */
	
	public void updateFromDB() {
		this.players = playerRepository.findAll();
	}
	
	/**
	 * Deletes all existing player objects in db, creates new objects and saves to DB
	 * @param playerProfiles list of player profiles to create player objects from
	 * @return void
	 */
	
	public void initialLoad(List<PlayerProfile> playerProfiles) {
		playerRepository.deleteAll();
		for (PlayerProfile newProfile : playerProfiles) {
				this.addPlayer(newProfile);
		};
		this.savePlayers();
	}
	
	/**
	 * Adds a new player to the players list and completes team data.  DOES NOT SAVE TO Database
	 * @param playerProfiles list of player profiles to create player objects from
	 * @return void
	 */
	
	public void addPlayer(PlayerProfile playerProfile) {
		Player newPlayer = new Player();
		newPlayer.setPlayerProfile(playerProfile);
		newPlayer.getPlayerProfile().setTeam(teamService.getTeamByCode(newPlayer.getPlayerProfile().getTeam().getCode()));
		this.players.add(newPlayer);
	}
	
	/**
	 * Stores the current players array in database
	 * @return void
	 */
	
	public void savePlayers() {
		playerRepository.saveAll(this.players);
	}
	
	
	/**
	 * Updates database record for the player provided.
	 * @param player
	 * @return void
	 */
	
	public void savePlayer(Player player) {
		playerRepository.save(player);
	}
	
	/**
	 * Updates the playerProfile for the Player matched using the playerCode. DOES NOT persist the player record.
	 * @param updatedplayerProfile
	 * @throws ApplicationException when multiple players with the same code exist
	 * @return void
	 */
	
	public void updateProfile(PlayerProfile profile) {
		try {
			Player oldPlayer =	ListUtil.getSingleFromList(this.players.stream()
				.filter(e -> e.getPlayerProfile().getCode() == profile.getCode()).collect(Collectors.toList()));
			profile.setFantasyId2017(oldPlayer.getPlayerProfile().getFantasyId2017());
			oldPlayer.setPlayerProfile(profile);
		}
		catch (NoSuchElementException e) {
			this.addPlayer(profile);
		}
	}
	
	/**
	 * Updates the historySeasons for the Player matched using the playerCode. DOES NOT persist the player record.
	 * @param int playerCode
	 * @param historySeasons array
	 * @throws ApplicationException when multiple players with the same code exist
	 * @throws NoSuchElementException when player cannot be found using the code
	 * @return void
	 */
	
	
	public void updateHistorySeasons(int code, List<HistorySeason> historySeasons) {
		ListUtil.getSingleFromList(this.players.stream()
			.filter(e -> e.getPlayerProfile().getCode() == code).collect(Collectors.toList())).setHistorySeasons(historySeasons);
	}
	
	/**
	 * Updates the list of performances for the Player matched using the playerCode. The method completes the fixture from the FantasyPL format (id only). DOES NOT persist the performance records.
	 * @param int playerCode
	 * @param List<FullPerformance> performances
	 * @param bool retain existing predictions when updating performances
	 * @return void
	 */
	
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
	
	/**
	 * Completes the fixture data from fantasyId provided by FantasyPL website.
	 * @param FullPerformance performance
	 * @param String season in the "2018/19" format
	 * @return void
	 */
	
	public void completeFixtureData(FullPerformance fullPerformance, String season) {
		fullPerformance.setFixture(fixtureService.getFixtureByFantasyIdAndSeason(fullPerformance.getFixture().getFantasyId(), season));
	}
	
	/**
	 * Makes predictions for a given, player, number of games and prediction method.
	 * @param Player player
	 * @param int number of future games for which the predictions need to be made
	 * @param AbstractPredictionMethod - the method to be used for prediction
	 * @return void
	 */
	
	public void makePredictions(Player player, int number, AbstractPredictionMethod method) {
		
		List<Prediction> predictions = predictionService.makePredictions(player, number, method);
		for (Prediction prediction : predictions) {
			FullPerformance performance = this.getCurrentPerformanceByPlayerAndFixtureCode(player, prediction.getFixtureCode());
			if (null == performance) performance = this.createNewPerformance(player, prediction.getFixtureCode());
			if ((!this.predictionExists(performance, prediction)))
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
	
	public void addPredictionToPlayerPerformance(Prediction prediction, FullPerformance performance) {
			if (!this.predictionExists(performance, prediction)) {
				performance.addPrediction(prediction);
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
		List<FullPerformance> performances = this.getPlayers()
				     .stream()
				     .filter(x -> x.equals(player))
				     .findFirst()
				     .get()
				     .getPerformances()
				     .stream()
				     .filter(y -> y.getRound() == round)
				     .collect(Collectors.toList());
		return performances;
	}
	
	public List<FullPerformance> getPastPerformancesByPlayerRoundAndSeason(Player player, int round, String season) {
		
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
			try {
				switch (season) {
			case "2018/19" :
				return this.getPlayers().stream().filter(x -> x.getPlayerProfile().getFantasyId2018() == id).findFirst().get();
			case "2017/18" :
				return this.getPlayers().stream().filter(x -> x.getPlayerProfile().getFantasyId2017() == id).findFirst().get();
			default :
				throw new ApplicationException("No data for season "+ season);
			}
			}
			catch (NoSuchElementException e) {
				throw new ApplicationException("Player not found");
			}
	}
	
	public HistorySeason getHistorySeason(Player player, String season) {
		return ListUtil.getSingleFromList(player.getHistorySeasons().stream().filter(x -> x.getSeasonName().equalsIgnoreCase(season)).collect(Collectors.toList()));
	}
	
	public Boolean performanceExists(List<FullPerformance> haystack, FullPerformance needle) {
		return haystack.stream().filter(x -> x.getRound() == needle.getRound()).findFirst().isPresent();
	}
	
	public Boolean predictionExists(FullPerformance performance, Prediction prediction) {
			if (performance
				.getPredictions()
				.stream()
				.filter(x ->x.getFixtureCode() == prediction.getFixtureCode() &&
							(0.2 > Math.abs(x.getPredictedPoints() - prediction.getPredictedPoints()) && 
							x.getPredictionMethodName().equalsIgnoreCase(prediction.getPredictionMethodName()))
						)
				.findFirst()
				.isPresent()) return true;
			else return false;
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
				points += this.getMeanPrediction(performanceInRound);
				break;
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
	
	public FullPerformance getPerformanceBYPlayerAndFixture(Player player, Fixture fixture) {
		List<FullPerformance> performances = player.getPerformances().stream().filter(x -> x.getFixture().getCode() == fixture.getCode()).collect(Collectors.toList());
		switch (performances.size()) {
			case  1: return performances.get(0);
			case 0: throw new NoSuchElementException();
			default: throw new ApplicationException("Multiple Performances found for fixture.");
		}
	}
	

	public Prediction getLatestPrediction(FullPerformance performance) {
		if (performance.getPredictions().size() == 0) return null;
		else {
			return performance.getPredictions().stream().max(Comparator.comparing(Prediction::getDatePredictionMade, Comparator.nullsLast(Comparator.reverseOrder()))).get();
		}
	}
	
	
	public List<Prediction> getLatestPredictionPerMethod(FullPerformance performance) {
		List<Prediction> latestPredictions = new ArrayList<Prediction>();
		for (Prediction prediction : performance.getPredictions().stream().sorted(Comparator.comparing(Prediction::getDatePredictionMade, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toList())) {
			if (prediction.getPredictedPoints() > 0 &&
					!latestPredictions.stream().filter(p -> p.getPredictionMethodName().equals(prediction.getPredictionMethodName())).findFirst().isPresent()) {
				latestPredictions.add(prediction);
			}
		}
		return latestPredictions;
	}
	

	public double getAveragePrediction(FullPerformance performance) {
		double avg = 0;
		int ticker = 0;
		List<Prediction> predictions = this.getLatestPredictionPerMethod(performance);
		if (predictions != null && predictions.size() != 0) {
			for (Prediction prediction : this.getLatestPredictionPerMethod(performance)) {
				avg = (avg + prediction.getPredictedPoints());
				ticker++;
			}
			avg = avg / ticker;
		}
		return avg;
	}
	

	public double getMeanPrediction(FullPerformance performance) {
		List<Prediction> predictions = this.getLatestPredictionPerMethod(performance);
		double[] predictedPoints = new double[predictions.size()];
		predictions.stream().forEach(x -> predictedPoints[predictions.indexOf(x)] = x.getPredictedPoints().doubleValue());
		Median median = new Median();
		double medianValue = median.evaluate(predictedPoints);
		return medianValue;
	}
}
	

