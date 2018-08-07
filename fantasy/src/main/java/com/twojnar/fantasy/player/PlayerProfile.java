package com.twojnar.fantasy.player;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojnar.fantasy.team.Team;

public class PlayerProfile {
	
	@Field("first_name")
	private String firstName;
	
	@Field("second_name")
	private String lastName;
	
	private String photo;
	
	@JsonProperty("team")
	private Team team;
	
	@JsonProperty("now_cost")
	private int	nowCost;
	
	@JsonProperty("news_added")
	private String newAdded;
	
	@JsonProperty("chance_of_playing_this_round")
	private String chanceOfPlayingThisRound;
	
	@JsonProperty("chance_of_playing_next_round")
	private String chanceOfPlayingNextRound;
	
	@JsonProperty("value_form")
	private float valueForm;
	
	@JsonProperty("value_season")
	private float valueSeason;
	
	@JsonProperty("cost_change_start")
	private int costChangeStart;
	
	@JsonProperty("cost_change_event")
	private int costChangeEvent;
	
	@JsonProperty("cost_change_start_fall")
	private int costChangeStartFall;
	
	@JsonProperty("cost_change_event_fall")
	private int costChangeEventFall;
	
	@JsonProperty("in_dreamteam")
	private Boolean inDreamteam;
	
	@JsonProperty("dreamteam_count")
	private int dreamteamCount;
	
	@JsonProperty("selected_by_percent")
	private float selectedByPercent;
	
	@JsonProperty("form")
	private float form;
	
	@JsonProperty("transfers_out")
	private int transfersOut;
	
	@JsonProperty("transfers_in")
	private int transfersIn;
	
	@JsonProperty("transfers_out_event")
	private int transfersOutEvent;
	
	@JsonProperty("transfers_in_event")
	private int transfersInEvent;
	
	@JsonProperty("loans_in")
	private int loansIn;
	
	@JsonProperty("loans_out")
	private int loansOut;
	
	@JsonProperty("loaned_in")
	private int loanedIn;
	
	@JsonProperty("loaned_out")
	private int loanedOut;
	
	@JsonProperty("total_points")
	private int totalPoints;
	
	@JsonProperty("event_points")
	private int eventPoints;
	
	@JsonProperty("points_per_game")
	private float pointsPerGame;
	
	@JsonProperty("ep_this")
	private float expectedPointsThisRound;
	
	@JsonProperty("ep_next")
	private float expectedPointsNextRound;
	
	@JsonProperty("special")
	private Boolean special;
	
	@JsonProperty("minutes")
	private int minutes;
	
	@JsonProperty("goals_scored")
	private int goalsScored;
	
	@JsonProperty("assists")
	private int assists;
	
	@JsonProperty("clean_sheets")
	private int cleanSheets;
	
	@JsonProperty("goals_conceded")
	private int goalsConceded;
	
	@JsonProperty("own_goals")
	private int ownGoals;
	
	@JsonProperty("penalties_saved")
	private int penaltiesSaved;
	
	@JsonProperty("penalties_missed")
	private int penaltiesMissed;
	
	@JsonProperty("yellow_cards")
	private int yellowCards;
	
	@JsonProperty("red_cards")
	private int redCards;
	
	@JsonProperty("saves")
	private int saves;
	
	@JsonProperty("bonus")
	private int bonus;
	
	@JsonProperty("bps")
	private int bonusPointsSystemTotal;
	
	@JsonProperty("influence")
	private float influence;
	
	@JsonProperty("creativity")
	private float creativity;
	
	@JsonProperty("threat")
	private float threat;
	
	@JsonProperty("ict_index")
	private float ictIndex;
	
	@JsonProperty("ea_index")
	private float eaIndex;
	
	@JsonProperty("element_type")
	private int position;

}
