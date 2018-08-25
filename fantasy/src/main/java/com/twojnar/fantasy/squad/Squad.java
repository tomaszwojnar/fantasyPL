package com.twojnar.fantasy.squad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Squad {
	
	@JsonIgnore
	private String id;
	
	@JsonProperty("id")
	private int fantasy_id;
	
	private int overall_points;

	private int value;
	
	private int bank;
	
	private List<Lineup> lineups;
	
	private int event_transfers;
	
	private int event_transfers_cost;
	
	private int extra_free_transfers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFantasyId() {
		return fantasy_id;
	}

	public void setFantasyId(int fantasyId) {
		this.fantasy_id = fantasyId;
	}

	public int getOverall_points() {
		return overall_points;
	}

	public void setOverall_points(int overall_points) {
		this.overall_points = overall_points;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public List<Lineup> getLineups() {
		return lineups;
	}

	public void setLineups(List<Lineup> lineups) {
		this.lineups = lineups;
	}

	public int getEvent_transfers() {
		return event_transfers;
	}

	public void setEvent_transfers(int event_transfers) {
		this.event_transfers = event_transfers;
	}

	public int getEvent_transfers_cost() {
		return event_transfers_cost;
	}

	public void setEvent_transfers_cost(int event_transfers_cost) {
		this.event_transfers_cost = event_transfers_cost;
	}

	public int getExtra_free_transfers() {
		return extra_free_transfers;
	}

	public void setExtra_free_transfers(int extra_free_transfers) {
		this.extra_free_transfers = extra_free_transfers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasy_id;
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
		Squad other = (Squad) obj;
		if (fantasy_id != other.fantasy_id)
			return false;
		return true;
	}
	
	
	
	

}
