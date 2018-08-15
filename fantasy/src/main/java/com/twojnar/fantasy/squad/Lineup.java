package com.twojnar.fantasy.squad;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lineup {
	
	private int event;
	
	@JsonProperty("active_chip")
	private String activeChip;
	
	@JsonProperty("automatic_subs")
	List<Map<String, Object>> automaticSubs;
	
	@JsonProperty("entry_history")
	List<Map<String, Object>> eventSummary;
	
	@JsonProperty("picks")
	List<Pick> picks;
	
	List<Advice> adviceGiven;

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public String getActiveChip() {
		return activeChip;
	}

	public void setActiveChip(String activeChip) {
		this.activeChip = activeChip;
	}

	public List<Map<String, Object>> getAutomaticSubs() {
		return automaticSubs;
	}

	public void setAutomaticSubs(List<Map<String, Object>> automaticSubs) {
		this.automaticSubs = automaticSubs;
	}

	public List<Map<String, Object>> getEventSummary() {
		return eventSummary;
	}

	public void setEventSummary(List<Map<String, Object>> eventSummary) {
		this.eventSummary = eventSummary;
	}

	public List<Pick> getPicks() {
		return picks;
	}

	public void setPicks(List<Pick> picks) {
		this.picks = picks;
	}

	public List<Advice> getAdviceGiven() {
		return adviceGiven;
	}

	public void setAdviceGiven(List<Advice> adviceGiven) {
		this.adviceGiven = adviceGiven;
	}
	
	

}
