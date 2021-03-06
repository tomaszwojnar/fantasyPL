package com.twojnar.fantasy.common;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.twojnar.scrapper.RequestService;

@Component
public class FantasyStatus {
	
	@Autowired
	RequestService requestService;
	
	private int currentEvent;
	
	private Date currentDeadLine;
	
	private Boolean finished;
	
	private Boolean dataChecked;
	
	@Value("${currentSeason}")
	private String currentSeason;
	
	/**
	 * Method connects to FPL website updates the status of the game
	 * 
	 * @return void
	 * @throws ApplicationException - exception is thrown when the data from FPL cannot be parsed
	 */
	
	static final Logger logger = LoggerFactory.getLogger(FantasyStatus.class);

	
	public void updateStatus() {
		JSONObject response;
		try {
			response = new JSONObject(requestService.sendRequest(requestService.defineRequest("https://fantasy.premierleague.com/drf/bootstrap-static")));
			this.currentEvent = response.getInt("current-event");
			this.currentDeadLine = new Date(Long.parseLong(response.getJSONArray("events").getJSONObject(this.currentEvent-1).getString("deadline_time_epoch"))*1000);
			this.finished = Boolean.parseBoolean(response.getJSONArray("events").getJSONObject(this.currentEvent-1).getString("finished"));
			this.dataChecked = Boolean.parseBoolean(response.getJSONArray("events").getJSONObject(this.currentEvent-1).getString("data_checked"));
			this.currentSeason = "2018/19";
		} catch (NumberFormatException | JSONException e) {
			logger.error("JSON from FPL could not be parsed");
		}

	}
	

	public int getCurrentEvent() {
		return currentEvent;
	}

	public Date getCurrentDeadline() {
		return currentDeadLine;
	}

	public void setCurrentDeadline(Date currentDeadline) {
		this.currentDeadLine = currentDeadline;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public Boolean getDataChecked() {
		return dataChecked;
	}

	public void setDataChecked(Boolean dataChecked) {
		this.dataChecked = dataChecked;
	}

	public void setCurrentEvent(int currentEvent) {
		this.currentEvent = currentEvent;
	}

	public String getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(String currentSeason) {
		this.currentSeason = currentSeason;
	}
}
