package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@Service
@EnableMongoRepositories(basePackageClasses = PlayerRepository.class)
public class PlayerService {
	
	@Autowired
	private TeamService teamService;
	
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
				newPlayer.getPlayerProfile().setTeam(teamService.getTeamByFantasyId(newPlayer.getPlayerProfile().getTeam().getFantasyId()));
				this.players.add(newPlayer);
		};
		this.savePlayers();
	}
	
	public void savePlayers() {
		playerRepository.saveAll(this.players);
	}
	
	public void updateProfile(PlayerProfile profile) {
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getFantasyId() == profile.getFantasyId()).findFirst().ifPresent(x -> {
			x.setPlayerProfile(profile);
			x.getPlayerProfile().setTeam(teamService.getTeamByFantasyId(x.getPlayerProfile().getTeam().getFantasyId()));
		});
	}
	
	public void updateHistorySeasons(int fantasyId, List<HistorySeason> historySeasons) {
		this.players.stream()
		.filter(e -> e.getPlayerProfile().getFantasyId() == fantasyId).findFirst().ifPresent(x -> {
			x.setHistorySeasons(historySeasons);
		});
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	
}

