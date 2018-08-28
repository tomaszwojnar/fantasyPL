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
	
	@Autowired
	private SimpleRegressionPastSeason simpleRegressionPastSeason;
	
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
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == profile.getCode()).findFirst().ifPresentOrElse(x -> {
			profile.setFantasyId2017(x.getPlayerProfile().getFantasyId2017());
			x.setPlayerProfile(profile);
			x.getPlayerProfile().setTeam(teamService.getTeamByFantasyId2018(x.getPlayerProfile().getTeam().getFantasyId2018()));
		},
				() -> this.addPlayer(profile));
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
			.forEach(x -> x.setPredictions(this.getPerformanceByPlayerAndRound(player, x.getRound()).getPredictions()));
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
			Performance performance = this.getPerformanceByPlayerAndRound(player, prediction.getRound());
			
			if (
					null == performance.getLatestPredictionByMethod(prediction.getPredictionMethodName())
					|| 0 != performance.getLatestPredictionByMethod(prediction.getPredictionMethodName()).getPredictedPoints().compareTo(prediction.getPredictedPoints()) 
				) 
			{
				performance.addPrediction(prediction);
			}

		}
		this.savePlayer(player);
	}
	
	/**
	 * Adds a preditction to an existing player performance. If none exists creates a new performance.
	 * 
	 * 
	 */
	
	public void addPredictionToPlayerPerformance(Prediction prediction, Player player, Fixture fixture) {
		player.getPerformances()
			  .stream()
			  .filter(x -> x.getRound() == fixture.getEvent())
			  .findFirst()
			  .ifPresentOrElse(
					  x -> {if (!this.predictionExists(player, prediction)) x.addPrediction(prediction);},
					  () -> {
						  FullPerformance performance = new FullPerformance();
						  performance.setFixture(fixture);
						  performance.setRound(fixture.getEvent());
						  performance.addPrediction(prediction);
						  player.getPerformances().add(performance);
					  }	  		
		);
	}
	
	
	public FullPerformance getPerformanceByPlayerAndRound(Player player, int round) {
		List<FullPerformance> performances = this.getPlayers().stream().filter(x -> x.equals(player)).findFirst().get().getPerformances().stream().filter(y -> y.getRound() == round).collect(Collectors.toList());
		if (performances.size() == 0) {
			FullPerformance performance = new FullPerformance();
			performance.setRound(round);
			performance.setFixture(fixtureService.getFixturesForTeam(player.getPlayerProfile().getTeam()).stream().filter(x -> x.getEvent() == round).findFirst().get());
			player.getPerformances().add(performance);
			this.savePlayer(player);
			return performance;
		}
		else if (performances.size() == 1) {
			return performances.get(0);
		}
		
		else {
			throw new NoSuchElementException("Multiple performances - db problem");
		}
	}
	
	public Performance getPastPerformanceByPlayerRoundAndSeason(Player player, int round, String season) {
		
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
								  .findFirst()
								  .get();
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
		return this.getPerformanceByPlayerAndRound(player, prediction.getRound())
			.getPredictions()
			.stream()
			.filter(x -> x.getPredictedPoints().equals(prediction.getPredictedPoints()) && x.getPredictionMethodName().equalsIgnoreCase(prediction.getPredictionMethodName()))
			.findFirst()
			.isPresent();
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
			points += this.getPerformanceByPlayerAndRound(player, currentEvent + i).getAveragePrediction();
		}
		return points;
	}
	
	public double getPredictedPointsForNextXGamesUsingMethod(Player player, int noOfGames, String method) {
		double points = 0;
		int currentEvent = fantasyStatus.getCurrentEvent();
		for (int i = 1; i <= noOfGames; i++) {
			if (currentEvent + i > 38) break;
			points += this.getPerformanceByPlayerAndRound(player, currentEvent + i).getLatestPredictionByMethod(method).getPredictedPoints();
		}
		return points;
	}
}
	

