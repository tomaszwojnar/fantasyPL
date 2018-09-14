package com.twojnar.fantasy.squad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.twojnar.fantasy.common.ApplicationException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.scrapper.ScrapperService;

@CacheConfig(cacheNames="squads")
@EnableMongoRepositories
@Service
public class SquadService {
	
	@Autowired
	FantasyStatus fantasyStatus;
	
	@Autowired
	SquadRepository squadRepository;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	ScrapperService scrapper;
	
	@Cacheable
	public Squad getSquadByFantasyId(int id) {
		return squadRepository.findByFantasyId(id);
	}
	
	public void saveSquad(Squad squad) {
		squadRepository.insert(squad);
	}
	
	public Squad addSquad(int squadId) {
		Squad squad = this.downloadData(squadId);
		this.saveSquad(squad);
		return squad;
	}
	
	@SuppressWarnings("unchecked")
	public Squad downloadData(int id) {
		Squad squad = new Squad();
		
		List<Squad> squads;
		try {
			squads = (List<Squad>) scrapper.scrapField("https://fantasy.premierleague.com/drf/entry/" + id, "entry", new Squad());
		} catch (IOException | JSONException e) {
			throw new ApplicationException("FantasyPL seems unreachable.");
		}
		if (squads.size() == 1) {
			squad = squads.get(0);
		}
		squad.setLineups(new ArrayList<Lineup>());
		for (int i = 1; i <= fantasyStatus.getCurrentEvent(); i++) {
			List<Lineup> lineups;
			try {
				lineups = (List<Lineup>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/entry/" + id +"/event/" + i +"/picks", new Lineup());
			} catch (IOException e) {
				throw new ApplicationException("FantasyPL seems unreachable.");
			}
			Lineup lineup = lineups.get(0);
			for (Pick pick : lineup.getPicks()) {
				pick.setPerformances(playerService.getPerformancesByPlayerAndRound(
						playerService.getPlayerByFantasyIdAndSeason(pick.getPlayerProfile().getFantasyId2018(), "2018/19"), i));
			}
			lineup.setEvent(lineup.getEntryHistory().getEvent());
			squad.getLineups().add(lineup);
		}
		squad.setLastChecked(new Date());
		return squad;
	}
	
	@SuppressWarnings("unchecked")
	@CacheEvict
	public Squad updateData(int id) {
		Squad existingSquad = this.getSquadByFantasyId(id);
		Squad squad = new Squad();
			List<Squad> squads;
			try {
				squads = (List<Squad>) scrapper.scrapField("https://fantasy.premierleague.com/drf/entry/" + id, "entry", new Squad());
			}
			catch (IOException e) {
				throw new ApplicationException("FantasyPL seems unreachable.");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				throw new ApplicationException("JSON response from Fantasy PL not parseable");
			}
		if (squads.size() == 1) {
			squad = squads.get(0);
		}
		squad.setLineups(existingSquad.getLineups());
		squad.setId(existingSquad.getId());
		for (int i = squad.getLatestLineup().getEvent(); i <= fantasyStatus.getCurrentEvent(); i++) {
			List<Lineup> lineups = new ArrayList<Lineup>();
			try {
				lineups = (List<Lineup>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/entry/" + id +"/event/" + i +"/picks", new Lineup());
			} catch (IOException e) {
				throw new ApplicationException("FantasyPL seems unreachable.");
			}
			Lineup lineup = lineups.get(0);
			for (Pick pick : lineup.getPicks()) {
				pick.setPerformances(playerService.getPerformancesByPlayerAndRound(
						playerService.getPlayerByFantasyIdAndSeason(pick.getPlayerProfile().getFantasyId2018(), "2018/19"), i));
			}
			lineup.setEvent(lineup.getEntryHistory().getEvent());
			squad.getLineups().add(lineup);
		}
		squad.setLastChecked(new Date());
		existingSquad = squad;
		this.saveSquadToDB(existingSquad);
		return existingSquad;
	}
	
	@Nullable
	public Squad getUpdatedSquad(int id) {
		Squad squad = this.getSquadByFantasyId(id);
		if (squad != null && squad.getLastChecked().before(fantasyStatus.getCurrentDeadline())) {
				return this.updateData(id);
		}
		else return squad;
	}
	
	public Lineup getLineUp(Squad squad, int eventId) throws JsonParseException, JsonMappingException, IOException, JSONException {
		return squad.getLineupByEvent(eventId);
	}
	
	public void addLineup(Squad squad, Lineup lineup) {
		Optional <Lineup> optionalLineup = squad.getLineups().stream().filter(line -> line.equals(lineup)).findFirst();
		if (optionalLineup.isPresent()) {
			squad.getLineups().remove(optionalLineup.get());
			squad.getLineups().add(lineup);
		}
		else {
			squad.getLineups().add(lineup);
		}
	}
	
	public void saveSquadToDB(Squad squad) {
		squadRepository.save(squad);
	}
	
	public List<Swap> findBetterAlternatives(Player player, int priceOffset, int noOfGames, int maxOptions) {
		int position = player.getPlayerProfile().getPosition();
		int price = player.getPlayerProfile().getNowCost();
		double avgPoints = playerService.getPredictedPointsForNextXGames(player, noOfGames);
		List<Swap> swaps = new ArrayList<Swap>();
		playerService
			.getPlayers()
			.stream()
			.filter(x ->
				x.getPlayerProfile().getPosition() == position &&
				x.getPlayerProfile().getNowCost() <= price + priceOffset &&
				avgPoints < playerService.getPredictedPointsForNextXGames(x, noOfGames)
			)
			.limit(maxOptions)
			.forEach(x ->{
				Swap swap = new Swap(
						player.getPlayerProfile(), 
						x.getPlayerProfile(), 
						Math.round(playerService.getPredictedPointsForNextXGames(x, noOfGames) - playerService.getPredictedPointsForNextXGames(player, noOfGames)));
				swaps.add(swap);
			}
			);
		return swaps;
	}
	
	public List<Swap> findBetterAlternativesUsingMethod(Player player, int priceOffset, int noOfGames, int maxOptions, String method) {
		int position = player.getPlayerProfile().getPosition();
		int price = player.getPlayerProfile().getNowCost();
		double points = playerService.getPredictedPointsForNextXGamesUsingMethod(player, noOfGames, method);
		List<Swap> swaps = new ArrayList<Swap>();
		playerService
			.getPlayers()
			.stream()
			.filter(x ->
				x.getPlayerProfile().getPosition() == position &&
				x.getPlayerProfile().getNowCost() <= price + priceOffset &&
				points < playerService.getPredictedPointsForNextXGamesUsingMethod(x, noOfGames, method)
			)
			.limit(maxOptions)
			.forEach(x ->{
				Swap swap = new Swap(
						player.getPlayerProfile(), 
						x.getPlayerProfile(), 
						Math.round(points - playerService.getPredictedPointsForNextXGamesUsingMethod(x, noOfGames, method)));
				swaps.add(swap);
			}
			);
		return swaps;
	}
	
	public PlayerAdvice getTransferAdvice(Squad squad) throws JsonParseException, JsonMappingException, IOException, JSONException {
		PlayerAdvice advice = new PlayerAdvice();
		for (Pick pick : squad.getLatestLineup().getPicks()) {
			this.findBetterAlternatives
				(
					playerService.getPlayerByCode(pick.getPlayerProfile().getCode()),
					squad.getBank(),
					5,
					3			
				).stream().forEach(x -> advice.addSwap(x));
		}
		return advice.sort().limit(10);
	}
	
	public PlayerAdvice getTransferAdviceUsingMethod(Squad squad, String method) throws JsonParseException, JsonMappingException, IOException, JSONException {
		PlayerAdvice advice = new PlayerAdvice();
		for (Pick pick : squad.getLatestLineup().getPicks()) {
			int noOfGames = ("FantasyPLWebsite".equals(method)) ? 1 : 5;
			this.findBetterAlternativesUsingMethod
				(
					playerService.getPlayerByCode(pick.getPlayerProfile().getCode()),
					squad.getBank(),
					noOfGames,
					3,
					method
				).stream().forEach(x -> advice.addSwap(x));
		}
	return advice.sort().limit(10);
	}
	
	
}
