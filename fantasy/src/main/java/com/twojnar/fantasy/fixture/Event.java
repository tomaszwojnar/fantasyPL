package com.twojnar.fantasy.fixture;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "events")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	
	
	public Event() {
		super();
	}
	
	public Event(int fantasy_id) {
		super();
		this.fantasy_id = fantasy_id;
	}
	
	@JsonIgnore
	private String season;
	
	@JsonIgnore
	private String id;
	
	@Field("fantasyId")
	@JsonProperty("id")
	private int fantasy_id;
	
	private int average_entry_score;
	
	private Boolean finished;
	
	private Boolean data_checked;
	
	private int highest_scoring_entry;
	
	private Date deadline_time;
	
	private int highest_score;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFantasy_id() {
		return fantasy_id;
	}

	public void setFantasy_id(int fantasy_id) {
		this.fantasy_id = fantasy_id;
	}

	public int getAverage_entry_score() {
		return average_entry_score;
	}

	public void setAverage_entry_score(int average_entry_score) {
		this.average_entry_score = average_entry_score;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean getData_checked() {
		return data_checked;
	}

	public void setData_checked(Boolean data_checked) {
		this.data_checked = data_checked;
	}

	public int getHighest_scoring_entry() {
		return highest_scoring_entry;
	}

	public void setHighest_scoring_entry(int highest_scoring_entry) {
		this.highest_scoring_entry = highest_scoring_entry;
	}

	public Date getDeadline_time() {
		return deadline_time;
	}

	public void setDeadline_time(Date deadline_time) {
		this.deadline_time = deadline_time;
	}

	public int getHighest_score() {
		return highest_score;
	}

	public void setHighest_score(int highest_score) {
		this.highest_score = highest_score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasy_id;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
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
		Event other = (Event) obj;
		if (fantasy_id != other.fantasy_id)
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		return true;
	}
	
	
	
}
