package com.twojnar.fantasy.squad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.twojnar.fantasy.player.Player;

public class Pick {
	
	@JsonProperty("element")
	private Player playerId;
	
	private int position;
	
	private Boolean isCaptain;
	
	private Boolean isViceCaptain;
	
	private int mutliplier;

}
