package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamRepository;
import com.twojnar.fantasy.team.TeamService;
import com.twojnar.scrapper.FantasyTeamResponse;
import com.twojnar.scrapper.ScrapperService;

@Service
public class TasksRunner implements CommandLineRunner {
	
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	ScrapperService scrapper;
	
	@Override
	public void run(String... args) throws Exception {
		try {
			this.updateTeams();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateTeams() throws IOException {
		FantasyTeamResponse teams = new FantasyTeamResponse();
		List<Team> teamsList = (List<Team>) scrapper.scrap("https://fantasy.premierleague.com/drf/teams", teams);
		teamService.setTeams(teamsList);
		/*System.out.println(teamService.getTeams().size());*/
		System.out.println(teamService.getTeams().size());
		
	}

}
