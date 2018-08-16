package com.twojnar.fantasy.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.SimplifiedPerformance;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@Component
public class StatsTests {
	
	@Autowired
	TasksRunner taskRunner;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	@Autowired
	PlayerService playerService;
	
	/*
	

	public void run() throws Exception {
    	teamService.updateFromDB();
    	fixtureService.updateFromDB();
    	playerService.updateFromDB();
    	
        BufferedWriter writer = null;

			try {
				writer = Files.newBufferedWriter(Paths.get("LukakuTest"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			CSVPrinter csvPrinter = null;
			try {
				csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
				            .withHeader("TeamStrength", "Opponent", "OpponentStrength", "DifferenceInStrength", "Points"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	
    	Player player = playerService.getPlayerByName("Lukaku");
    	
    	HistorySeason historySeason = player.getHistorySeasonByName("2017/18");
    	
    	String team = historySeason.getTeam();
		Team foundTeam = null;
		try {
			foundTeam = teamService.findByName(team);
		}
			catch (NoSuchElementException e) {	
		}
		
		int strengthHome = (foundTeam != null) ? foundTeam.getStrength_overall_home() : 1000;
		int strengthAway = (foundTeam != null) ? foundTeam.getStrength_overall_away() : 1000;
    	
		double[] x = new double[38];
    	double[] y = new double[38];
    	int index = 0;
    	
    	/*for (SimplifiedPerformance performance : historySeason.getHistoryPerformances()) {
    		x[index] = performance.getTotalPoints();
	        int strengthTeam = (performance.getWasHome()) ? strengthHome : strengthAway;
	        y[index] = strengthTeam - performance.getOpponentStrength();
	        index++;
			try {
				csvPrinter.printRecord(strengthTeam, performance.getOpponentTeam(), performance.getOpponentStrength(), strengthTeam - performance.getOpponentStrength(), performance.getTotalPoints());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	csvPrinter.flush();
    	

    	
	
		PearsonsCorrelation pc = new PearsonsCorrelation();
		System.out.print("Pearson\'s coefficient of correlation: "+pc.correlation(x,y));
		SpearmansCorrelation sc = new SpearmansCorrelation();
		System.out.println("Spearman\'s Rank Correlation: "+sc.correlation(x,y));
		Covariance cov = new Covariance();
		System.out.println("Covariance: "+cov.covariance(x,y));


	}*/
}
