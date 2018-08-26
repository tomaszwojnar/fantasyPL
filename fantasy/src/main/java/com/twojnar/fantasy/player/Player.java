package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "players")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
	
	@Autowired
	PlayerService playerService;
	
	@JsonIgnore
	private String id;
	
	private PlayerProfile playerProfile;
	
	@JsonIgnore
	private List<PlayerAggregates> aggregates;
	
	private List<HistorySeason> historySeasons = new ArrayList<HistorySeason>();
	
	private List<FullPerformance> performances = new ArrayList<FullPerformance>();
	

	public PlayerProfile getPlayerProfile() {
		return playerProfile;
	}

	public void setPlayerProfile(PlayerProfile playerProfile) {
		this.playerProfile = playerProfile;
	}

	public List<HistorySeason> getHistorySeasons() {
		return historySeasons;
	}

	public void setHistorySeasons(List<HistorySeason> historySeasons) {
		this.historySeasons = historySeasons;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<FullPerformance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<FullPerformance> performances) {
		this.performances = performances;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (this.getPlayerProfile().getCode() != other.getPlayerProfile().getCode())
			return false;
		return true;
	}

	public List<PlayerAggregates> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<PlayerAggregates> aggregates) {
		this.aggregates = aggregates;
	}

}
