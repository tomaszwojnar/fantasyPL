package com.twojnar.fantasy.squad;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.BeanUtil;
import com.twojnar.fantasy.common.View;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.PlayerProfile;
import com.twojnar.fantasy.player.PlayerService;

@JsonView(View.PublicGeneral.class)
public class Pick {
	
	
	@JsonProperty("element")
	private PlayerProfile playerProfile;
	
	@JsonView(View.PublicDetails.class)
	private List<FullPerformance> performances;
	
	@JsonProperty("position")
	private int position;
	
	@JsonProperty("is_captain")
	private Boolean isCaptain;
	
	@JsonProperty("is_vice_captain")
	private Boolean isViceCaptain;
	
	@JsonProperty("multiplier")
	private int mutliplier;

	public PlayerProfile getPlayerProfile() {
		return playerProfile;
	}

	public void setPlayerProfile(PlayerProfile playerProfile) {
		this.playerProfile = playerProfile;
	}
	
	public void setPlayerProfile(int id) {
		PlayerService playerService = BeanUtil.getBean(PlayerService.class);
		this.playerProfile = playerService.getPlayerByFantasyIdAndSeason(id, "2018/19").getPlayerProfile();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Boolean getIsCaptain() {
		return isCaptain;
	}

	public void setIsCaptain(Boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public Boolean getIsViceCaptain() {
		return isViceCaptain;
	}

	public void setIsViceCaptain(Boolean isViceCaptain) {
		this.isViceCaptain = isViceCaptain;
	}

	public int getMutliplier() {
		return mutliplier;
	}

	public void setMutliplier(int mutliplier) {
		this.mutliplier = mutliplier;
	}

	public List<FullPerformance> getPerformances() {
		return performances;
	}

	public void setPerformances(List<FullPerformance> performances) {
		this.performances = performances;
	}
	
	
	
	

}
