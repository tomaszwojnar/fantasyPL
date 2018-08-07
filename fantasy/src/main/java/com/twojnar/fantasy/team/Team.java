package com.twojnar.fantasy.team;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "teams")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements Serializable {
		
	private String id;
	
	@JsonProperty("id")
	private int fantasyId;
	private String name;
	private String code;
	private String short_name;
	private Boolean unavailable;
	private int stength;
	private int position;
	private int played;
	private int win;
	private int loss;
	private int draw;
	private int points;
	private String form;
	private String link_url;
    private int strength_overall_home;
    private int strength_overall_away;
    private int strength_attack_home;
    private int strength_attack_away;
    private int strength_defence_home;
    private int strength_defence_away;
    
    
    public Team () {
    	super();
    }
    
    public Team (int identifier) {
    	super();
    	this.fantasyId = identifier;
    }
    
    
	
    @JsonIgnore
    public String getId() {
		return id;
	}
    
    @JsonIgnore
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public Boolean getUnavailable() {
		return unavailable;
	}
	public void setUnavailable(Boolean unavailable) {
		this.unavailable = unavailable;
	}
	public int getStength() {
		return stength;
	}
	public void setStength(int stength) {
		this.stength = stength;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getPlayed() {
		return played;
	}
	public void setPlayed(int played) {
		this.played = played;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLoss() {
		return loss;
	}
	public void setLoss(int loss) {
		this.loss = loss;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public int getStrength_overall_home() {
		return strength_overall_home;
	}
	public void setStrength_overall_home(int strength_overall_home) {
		this.strength_overall_home = strength_overall_home;
	}
	public int getStrength_overall_away() {
		return strength_overall_away;
	}
	public void setStrength_overall_away(int strength_overall_away) {
		this.strength_overall_away = strength_overall_away;
	}
	public int getStrength_attack_home() {
		return strength_attack_home;
	}
	public void setStrength_attack_home(int strength_attack_home) {
		this.strength_attack_home = strength_attack_home;
	}
	public int getStrength_attack_away() {
		return strength_attack_away;
	}
	public void setStrength_attack_away(int strength_attack_away) {
		this.strength_attack_away = strength_attack_away;
	}
	public int getStrength_defence_home() {
		return strength_defence_home;
	}
	public void setStrength_defence_home(int strength_defence_home) {
		this.strength_defence_home = strength_defence_home;
	}
	public int getStrength_defence_away() {
		return strength_defence_away;
	}
	public void setStrength_defence_away(int strength_defence_away) {
		this.strength_defence_away = strength_defence_away;
	}
	
	public int getFantasyId() {
		return this.fantasyId;
	}
	public void setFantasyId(int fantasyId) {
		this.fantasyId = fantasyId;
	}
	@Override
	public String toString() {
		return "Team [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasyId;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Team other = (Team) obj;
		if (fantasyId != other.fantasyId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
