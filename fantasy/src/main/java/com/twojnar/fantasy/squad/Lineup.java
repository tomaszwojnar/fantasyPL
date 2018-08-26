package com.twojnar.fantasy.squad;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lineup {
	
	@JsonIgnore
	private int event;
	
	@JsonProperty("active_chip")
	private String activeChip;
	
	@JsonProperty("automatic_subs")
	List<Map<String, Object>> automaticSubs;
	
	@JsonProperty("entry_history")
	List<Map<String, Object>> eventSummary;
	
	@JsonProperty("picks")
	List<Pick> picks;
	
	@JsonIgnore
	List<Advice> adviceGiven;
	
	EntryHistory entryHistory;
	

	public class EntryHistory {
		private int points;
		private int total_points;
		private int rank;
		private int rank_sort;
		private int overall_rank;
		private int event_transfers;
		private int event_transfers_cost;
		private int value;
		private int points_on_bench;
		private int bank;
		public int getPoints() {
			return points;
		}
		public void setPoints(int points) {
			this.points = points;
		}
		public int getTotal_points() {
			return total_points;
		}
		public void setTotal_points(int total_points) {
			this.total_points = total_points;
		}
		public int getRank() {
			return rank;
		}
		public void setRank(int rank) {
			this.rank = rank;
		}
		public int getRank_sort() {
			return rank_sort;
		}
		public void setRank_sort(int rank_sort) {
			this.rank_sort = rank_sort;
		}
		public int getOverall_rank() {
			return overall_rank;
		}
		public void setOverall_rank(int overall_rank) {
			this.overall_rank = overall_rank;
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
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getPoints_on_bench() {
			return points_on_bench;
		}
		public void setPoints_on_bench(int points_on_bench) {
			this.points_on_bench = points_on_bench;
		}
		public int getBank() {
			return bank;
		}
		public void setBank(int bank) {
			this.bank = bank;
		}
		
		
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + event;
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
		Lineup other = (Lineup) obj;
		if (event != other.event)
			return false;
		return true;
	}
	
	
	
	

}
