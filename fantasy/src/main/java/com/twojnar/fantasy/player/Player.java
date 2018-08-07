package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "players")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
	
	@JsonIgnore
	private String id;
	
	@JsonProperty("id")
	private int fantasyId;
	
	private PlayerProfile playerProfile;
	
	private List<HistorySeason> historySeasons = new ArrayList<HistorySeason>();
	
	private List<Performance> performances = new ArrayList<Performance>();;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFantasyId() {
		return fantasyId;
	}

	public void setFantasyId(int fantasyId) {
		this.fantasyId = fantasyId;
	}

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

	public List<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasyId;
		return result;
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
		if (fantasyId != other.fantasyId)
			return false;
		return true;
	}
	
	
	
	
	

}
