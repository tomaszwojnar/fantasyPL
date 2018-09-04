package com.twojnar.fantasy.common;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerAggregates;
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
	
	public void run() throws IOException {
		
		//this.setAttackDefencePointsCorrelationAggregate();
		//this.setOverallPointsCorrelationAggregate();
		//this.setOverallPointsCorrelationAggregateFromSimilar();
		this.printer();
		
		
	}
	
	private void printer() throws IOException {
		
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("AllStats10.csv"));
        List<String> header = new ArrayList<String>();
        header.add("PlayerCode");
        header.add("PlayerLastName");
        header.add("Position");
        header.add("PlayerTeam");
        
        Method[] methods = playerService.getHistorySeason(playerService.getPlayerByCode(11334), "2017/18").getHistoryPerformanceByRound(1).getClass().getMethods();
        
        for (Method method : methods) {
        	if (method.getName().contains("get") && method.getReturnType().isPrimitive() && method.getParameterCount() == 0) {
        		header.add((String) method.getName().subSequence(3, method.getName().length()));
        	}
        }

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord(header);
      
        PearsonsCorrelation pc = new PearsonsCorrelation();
		SpearmansCorrelation sc = new SpearmansCorrelation();
		Covariance cov = new Covariance();
		
		playerService.getPlayers()
			.stream()
			.forEach(player -> {
				List<String> playerData = new ArrayList<String>();
				playerData.add(String.valueOf(player.getPlayerProfile().getCode()));
				playerData.add(player.getPlayerProfile().getLastName());
				playerData.add(String.valueOf(player.getPlayerProfile().getPosition()));
				playerData.add(String.valueOf(player.getPlayerProfile().getTeam()));
				player.getHistorySeasons().stream().filter(season -> season.getSeasonName().equals("2017/18")).findFirst().ifPresent(season -> {
			        for (Method method : methods) {
			        	if (method.getName().contains("get") && method.getReturnType().isPrimitive() && method.getParameterCount() == 0) {
			        		double[] feature = new double[28];
			        		double[] label = new double[28];
			        		season.getHistoryPerformances().stream().filter(performance -> performance.getRound()>11).forEach(performance -> {
			        			double average = 0;
			        			for (int i = 1; i <= 10; i++) {
				        			try {
				        				FullPerformance previousPerformance = season.getHistoryPerformanceByRound(performance.getRound()-i);
				        				if (previousPerformance != null) {
											average = (average + Double.parseDouble(String.valueOf(method.invoke(season.getHistoryPerformanceByRound(performance.getRound()-i))))) / i + 1;
				        				}
									} catch (IllegalAccessException | IllegalArgumentException
											| InvocationTargetException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										System.exit(1);
									}
			        			}
			        			feature[performance.getRound()-11] = average;
			        			label[performance.getRound()-11] = performance.getTotalPoints();

			        			
			        		});
			        		playerData.add(String.valueOf(pc.correlation(feature, label)));
			        	}
			        }
				});
				
				try {
					csvPrinter.printRecord(playerData);

				} catch (IOException e) {
					 //TODO Auto-generated catch block
					e.printStackTrace();
				}
				});
        csvPrinter.flush();
	}

	
	/*public void setOverallPointsCorrelationAggregate() {
		
        PearsonsCorrelation pc = new PearsonsCorrelation();
		playerService.getPlayers()
		.stream()
		.forEach(x -> {
	        double[] a = new double[38];
	        double[] b = new double[38];
	        int index = 0;
			x.getHistorySeasons().stream().filter(y -> y.getSeasonName().equals("2017/18") && y.getMinutes()>900).findFirst().ifPresent(z -> {
				z.getHistoryPerformances().stream().filter(u -> u.getMinutes()>45).forEach(q -> {
					int strengthDifference = (q.getWasHome()) ?
							q.getFixture().getHomeTeam().getStrength_overall_home() - q.getFixture().getAwayTeam().getStrength_overall_away() :
							q.getFixture().getAwayTeam().getStrength_overall_away() - q.getFixture().getHomeTeam().getStrength_overall_home();
					a[q.getRound()-1] = strengthDifference;
					b[q.getRound()-1] = q.getTotalPoints();
				});
			});
			if (x.getAggregates() == null) x.setAggregates(new ArrayList<PlayerAggregates>());
			else {
				x.getAggregates()
				.stream()
				.filter(y -> y.getSeason().equals("2017/18"))
				.findFirst()
				.ifPresentOrElse
					(
						z -> z.setOverallDiffPointsCorrelation(pc.correlation(a, b)),
						() -> {
							PlayerAggregates playerAggregates = new PlayerAggregates();
							playerAggregates.setSeason("2017/18");
							playerAggregates.setOverallDiffPointsCorrelation(pc.correlation(a, b));
							x.getAggregates().add(playerAggregates);
						}
						
					);

		}
			playerService.savePlayer(x);});
	}*/
	
	
	/*public void setOverallPointsCorrelationAggregateFromSimilar() {
		
        PearsonsCorrelation pc = new PearsonsCorrelation();
		playerService.getPlayers()
		.stream()
		.forEach(x -> {
	        double[] a = new double[38];
	        double[] b = new double[38];
			x.getHistorySeasons().stream().filter(y -> y.getSeasonName().equals("2017/18") && y.getMinutes()>900).findFirst().ifPresent(z -> {
				z.getHistoryPerformances().stream().forEach(q -> {
					int strengthDifference = (q.getWasHome()) ?
								q.getFixture().getHomeTeam().getStrength_overall_home() - q.getFixture().getAwayTeam().getStrength_overall_away():
								q.getFixture().getAwayTeam().getStrength_overall_away() - q.getFixture().getHomeTeam().getStrength_overall_home();
					double scoreForSimilarGames = 0;
					int ticker = 1;
					for (FullPerformance performance : z.getHistoryPerformances()) {
						if (Math.abs(performance.getFixture().getHomeTeam().getStrength_overall_home() - performance.getFixture().getAwayTeam().getStrength_overall_away()-strengthDifference)<75)
						{
							scoreForSimilarGames = (scoreForSimilarGames + performance.getTotalPoints()) / ticker;
							ticker++;
						}
					}
					
						a[q.getRound()-1] = scoreForSimilarGames;
						b[q.getRound()-1] = q.getTotalPoints();
			});
			
			if (x.getAggregates() == null) x.setAggregates(new ArrayList<PlayerAggregates>());
			else {
				x.getAggregates()
				.stream()
				.filter(y -> y.getSeason().equals("2017/18"))
				.findFirst()
				.ifPresentOrElse
					(
						f -> f.setOverallPointsCorrelationFromSimilarMatches(pc.correlation(a, b)),
						() -> {
							PlayerAggregates playerAggregates = new PlayerAggregates();
							playerAggregates.setSeason("2017/18");
							playerAggregates.setOverallPointsCorrelationFromSimilarMatches(pc.correlation(a, b));
							x.getAggregates().add(playerAggregates);
						}
						
					);

			}
			playerService.savePlayer(x);
			});
		});
	}*/
	
	/*public void setAttackDefencePointsCorrelationAggregate() {
		
        PearsonsCorrelation pc = new PearsonsCorrelation();
		playerService.getPlayers()
		.stream()
		.forEach(x -> {
	        double[] a = new double[38];
	        double[] b = new double[38];
	        int index = 0;
			x.getHistorySeasons().stream().filter(y -> y.getSeasonName().equals("2017/18") && y.getMinutes()>900).findFirst().ifPresent(z -> {
				z.getHistoryPerformances().stream().filter(u -> u.getMinutes() > 45).forEach(q -> {
					int strengthDifference = 0;
					if (q.getWasHome()) 
						{
						Team playerTeam = q.getFixture().getHomeTeam();
						Team opponentTeam = q.getFixture().getAwayTeam();
						switch (x.getPlayerProfile().getPosition()) {
						case 1:
						case 2:
							strengthDifference = playerTeam.getStrength_defence_home() - opponentTeam.getStrength_attack_away();
							break;
						case 3:
							strengthDifference = 
								playerTeam.getStrength_defence_home() +
								playerTeam.getStrength_attack_home() -
								opponentTeam.getStrength_defence_away() -
								opponentTeam.getStrength_defence_away();
							break;
						case 4:
							strengthDifference = playerTeam.getStrength_attack_home() - opponentTeam.getStrength_defence_away();
							break;
						}
						}
					else {
						Team playerTeam = q.getFixture().getAwayTeam();
						Team opponentTeam = q.getFixture().getHomeTeam();
						switch (x.getPlayerProfile().getPosition()) {
						case 1:
						case 2:
							strengthDifference = playerTeam.getStrength_defence_away() - opponentTeam.getStrength_attack_home();
							break;
						case 3:
							strengthDifference = 
								playerTeam.getStrength_defence_away() +
								playerTeam.getStrength_attack_away() -
								opponentTeam.getStrength_defence_home() -
								opponentTeam.getStrength_defence_home();
							break;
						case 4:
							strengthDifference = playerTeam.getStrength_attack_away() - opponentTeam.getStrength_defence_home();
							break;
						}
						
					
					}
					a[q.getRound()-1] = strengthDifference;
					b[q.getRound()-1] = q.getTotalPoints();
				});
							//q.getFixture().getHomeTeam().getStrength_overall_home() - q.getFixture().getAwayTeam().getStrength_overall_away() :
							//q.getFixture().getAwayTeam().getStrength_overall_away() - q.getFixture().getHomeTeam().getStrength_overall_home();
				});
			if (x.getAggregates() == null) x.setAggregates(new ArrayList<PlayerAggregates>());
			else {x.getAggregates()
				.stream()
				.filter(y -> y.getSeason().equals("2017/18"))
				.findFirst()
				.ifPresentOrElse
					(
						z -> z.setAttackDefencePointsCorrelation(pc.correlation(a, b)),
						() -> {
							PlayerAggregates playerAggregates = new PlayerAggregates();
							playerAggregates.setSeason("2017/18");
							playerAggregates.setOverallDiffPointsCorrelation(pc.correlation(a, b));
							x.getAggregates().add(playerAggregates);
						}
						
					);

		}
			playerService.savePlayer(x);});
	}*/
	

}
