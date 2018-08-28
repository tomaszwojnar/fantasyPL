package com.twojnar.fantasy.team;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;

@Document(collection = "teams")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView(View.Public.class)
public class Team implements Serializable {
		
	private String id;
	
	@JsonProperty("id")
	private int fantasyId2018;
	
	@JsonIgnore
	private int fantasyId2017;
	
	private String name;
	
	@JsonProperty("code")
	private int code;
	private String short_name;
	private Boolean unavailable;
	private int strength;
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
    
    public Team (int id) {
    	super();
    	this.fantasyId2018 = id;
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
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
	public int getStrength() {
		return strength;
	}
	public void setStrength(int stength) {
		this.strength = stength;
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
	
	public int getFantasyId2018() {
		return this.fantasyId2018;
	}
	public void setFantasyId2018(int fantasyId2018) {
		this.fantasyId2018 = fantasyId2018;
	}
	
	public int getFantasyId2017() {
		return this.fantasyId2017;
	}
	public void setFantasyId2017(int fantasyId2017) {
		this.fantasyId2017 = fantasyId2017;
	}
	
	@Override
	public String toString() {
		return "Team [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fantasyId2018;
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
		if (code != other.code)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
