package com.twojnar.fantasy.squad;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;
import com.twojnar.fantasy.player.predictions.Prediction;


@Document(collection = "squads")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView(View.Public.class)
public class Squad {
	
	@JsonIgnore
	private String id;
	
	@JsonIgnore
	private Date lastChecked;
	
	@JsonProperty("id")
	private int fantasyId;
	
	@JsonProperty("summary_overall_points")
	private int overall_points;

	private int value;
	
	private int bank;

	@JsonView(View.ExtendedPublic.class)
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
		return fantasyId;
	}

	public void setFantasyId(int fantasyId) {
		this.fantasyId = fantasyId;
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
		Squad other = (Squad) obj;
		if (fantasyId != other.fantasyId)
			return false;
		return true;
	}

	public Date getLastChecked() {
		return lastChecked;
	}

	public void setLastChecked(Date lastChecked) {
		this.lastChecked = lastChecked;
	}
	
	//public Squad withLatestPicksOnly() {
	//	this.setLineups(this.getLineups().stream().sorted(Comparator.comparing(Lineup::getEvent, Comparator.nullsLast(Comparator.reverseOrder()))).limit(1).collect(Collectors.toList()));
	//	return this;
	//}
	
	public Lineup getLatestLineup() {
		return this.getLineups().stream().sorted(Comparator.comparing(Lineup::getEvent, Comparator.nullsLast(Comparator.reverseOrder()))).limit(1).collect(Collectors.toList()).get(0);
	}
	
	public Lineup getLineupByEvent(int eventId) {
		return this.getLineups().stream().filter(x -> x.getEvent() == eventId).findFirst().get();
	}
	
	

}
