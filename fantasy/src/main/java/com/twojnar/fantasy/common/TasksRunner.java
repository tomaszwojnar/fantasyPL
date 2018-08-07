package com.twojnar.fantasy.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twojnar.fantasy.team.TeamService;


@Service
public class TasksRunner {
	
	@Autowired
	FixtureUpdateDefinition fixtureUpdateDefinition;
	
	@Autowired
	TeamsUpdateDefinition teamUpdateDefinition;
	
	@Autowired
	TeamService teamService;

	
	public void run(String... args) throws Exception {
		try {
			fixtureUpdateDefinition.updateFixtures();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
