package com.twojnar.fantasy.player;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "players")
public class Player {
	
	
	private String id;
	@Field("fantasyId")
	private int fantasyId;
	
	@Field("name")
	private String firstName;
	private String lastName;
	
	@Field("history_past")
	private ArrayList<HistorySeason> historicalSeasons;
	

	public ArrayList<HistorySeason> getHistoricalSeasons() {
		return historicalSeasons;
	}

	public void setHistoricalSeasons(ArrayList<HistorySeason> historicalSeasons) {
		this.historicalSeasons = historicalSeasons;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
