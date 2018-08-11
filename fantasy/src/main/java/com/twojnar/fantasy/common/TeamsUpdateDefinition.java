package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.FantasyTeamResponse;
import com.twojnar.scrapper.ScrapperService;

@Component
public class TeamsUpdateDefinition extends TaskDefinition {
	
	@Autowired
	ScrapperService scrapper;
	
	@Autowired
	TeamService teamService;
	
	public void updateTeams() throws IOException {
		FantasyTeamResponse teams = new FantasyTeamResponse();
		List<Team> teamsList = (List<Team>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/teams", teams);
		teamService.updateFromDB();
		teamService.updateTeams(teamsList);
		teamService.saveTeams();
	}
	
	public void initialLoad() throws IOException {
		FantasyTeamResponse teams = new FantasyTeamResponse();
		List<Team> teamsList = (List<Team>) scrapper.scrapAll("https://fantasy.premierleague.com/drf/teams", teams);
		teamService.initialLoad(teamsList);
	}

}
