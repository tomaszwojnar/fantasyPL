package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.predictions.Prediction;
import com.twojnar.fantasy.player.predictions.PredictionService;
import com.twojnar.fantasy.team.Team;
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
	
	List<Player> players = new ArrayList<Player>();
	
	public void updateFromDB() {
		this.players = playerRepository.findAll();
	}
	
	public void initialLoad(List<PlayerProfile> playerProfiles) {
		playerRepository.deleteAll();
		for (PlayerProfile newProfile : playerProfiles) {
				Player newPlayer = new Player();
				newPlayer.setPlayerProfile(newProfile);
				newPlayer.getPlayerProfile().setTeam(teamService.getTeamByFantasyId2018(newPlayer.getPlayerProfile().getTeam().getFantasyId2018()));
				this.players.add(newPlayer);
		};
		this.savePlayers();
	}
	
	
	public void savePlayers() {
		playerRepository.saveAll(this.players);
	}
	
	public void savePlayer(Player player) {
		playerRepository.save(player);
	}
	
	public void updateProfile(PlayerProfile profile) {
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == profile.getCode()).findFirst().ifPresent(x -> {
			x.setPlayerProfile(profile);
			x.getPlayerProfile().setTeam(teamService.getTeamByFantasyId2018(x.getPlayerProfile().getTeam().getFantasyId2018()));
		});
	}
	
	public void updateHistorySeasons(int code, List<HistorySeason> historySeasons) {
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == code).findFirst().ifPresent(x -> {
			x.setHistorySeasons(historySeasons);
		});
	}
	
	public void updatePerformances(int code, List<FullPerformance> performances) {
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getCode() == code).findFirst().ifPresent(x -> {
			x.setPerformances(performances);
		});
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public void completeTeam(FullPerformance fullPerformance) {
		fullPerformance.setOpponentTeam(teamService.getTeamByFantasyId2018(fullPerformance.getOpponentTeam().getFantasyId2018()));
	}
	
	public void completeFixture(FullPerformance fullPerformance) {
		fullPerformance.setFixture(fixtureService.getFixtureByFantasyId(fullPerformance.getFixture().getFantasyId()));
	}
	
	public void completePerformanceData(FullPerformance fullPerformance) {
		fullPerformance.setOpponentTeam(teamService.getTeamByFantasyId2018(fullPerformance.getOpponentTeam().getFantasyId2018()));
		fullPerformance.setFixture(fixtureService.getFixtureByFantasyId(fullPerformance.getFixture().getFantasyId()));
	}
	
	public void updatePredictions(Player player, int number) throws Exception {
		
		List<Prediction> predictions = predictionService.makePredictions(player, number);
		
		for (Prediction prediction : predictions) {
			Performance performance = this.getPerformanceByRoundAndPlayer(player, prediction.getFixture().getEvent());
			if (performance.getLatestPredictionByMethod(prediction.getPredictionMethod()) == null ||performance.getLatestPredictionByMethod(prediction.getPredictionMethod()).getPredictedPoints() != prediction.getPredictedPoints()) {
				performance.addPrediction(prediction);
			}
		}
		this.savePlayer(player);
	}
	
	public Performance getPerformanceByRoundAndPlayer(Player player, int round) throws Exception {
		List<Performance> performances = this.getPlayers().stream().filter(x -> x.equals(player)).findFirst().get().getPerformances().stream().filter(y -> y.getRound() == round).collect(Collectors.toList());
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
			throw new Exception("Multiple performances - db problem");
		}
	}

}

