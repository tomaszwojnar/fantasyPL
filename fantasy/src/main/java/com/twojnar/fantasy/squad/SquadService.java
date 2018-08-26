package com.twojnar.fantasy.squad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.scrapper.ScrapperService;

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
	
	public Squad getSquadByFantasyId(int id) {
		return squadRepository.findByFantasyId(id);
	}
	
	public void addSquad(Squad squad) {
		squadRepository.insert(squad);
	}
	
	public Squad downloadData(int id) throws JsonParseException, JsonMappingException, IOException, JSONException {
		Squad squad = new Squad();
		List<Squad> squads =  (List<Squad>) scrapper.scrapField("https://fantasy.premierleague.com/drf/entry/" + id, "entry", new Squad());
		if (squads.size() == 1) {
			squad = squads.get(0);
		}
		squad.setLineups(new ArrayList<Lineup>());
		for (int i = 1; i <= fantasyStatus.getCurrentEvent(); i++) {
			List<Lineup> lineups  =  (List<Lineup>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/entry/" + id +"/event/" + i +"/picks", new Lineup());
			Lineup lineup = lineups.get(0);
			for (Pick pick : lineup.getPicks()) {
				pick.setPerformance(playerService.getPerformanceByPlayerAndRound(
						playerService.getPlayerByFantasyIdAndSeason(pick.getPlayerProfile().getFantasyId2018(), "2018/19"), i));
			}
			lineup.setEvent(i);
			squad.getLineups().add(lineup);
		}
		squad.setLastChecked(new Date());
		this.saveSquadToDB(squad);
		return squad;
	}
	
	public void addLineup(Squad squad, Lineup lineup) {
		squad.getLineups().stream().filter(line -> line.equals(lineup)).findFirst().ifPresentOrElse(
				line -> {squad.getLineups().remove(line); squad.getLineups().add(lineup);},
				() -> squad.getLineups().add(lineup));
	}
	
	public void saveSquadToDB(Squad squad) {
		squadRepository.save(squad);
	}
	
	

}
