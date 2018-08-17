package com.twojnar.fantasy.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.TeamService;

@Component
public class ApplicationInit {
	
	@Autowired
	TasksRunner taskRunner;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	FantasyStatus fantasyStatus;
	

	public void run() throws Exception {
    	
		fantasyStatus.updateStatus();
		teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	playerService.updateFromDB();
    	
    	taskRunner.run();
    	

	}

}
