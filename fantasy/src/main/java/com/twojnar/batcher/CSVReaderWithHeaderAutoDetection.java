package com.twojnar.batcher;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.Fixture;
import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.FullPerformance;
import com.twojnar.fantasy.player.HistorySeason;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;


@Component
public class CSVReaderWithHeaderAutoDetection {
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;
	
	
	public void processTeamsCSV(String filePath) {
		
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
    	
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) 
        {
        	for (CSVRecord csvRecord : csvParser) {
	        	int code = Integer.parseInt(csvRecord.get("code"));

				try {
					Team team = teamService.getTeams().stream().filter(x -> x.getCode() == code).findFirst().get();
					team.setFantasyId2017(Integer.parseInt(csvRecord.get("id")));
					}
	
	        	catch (NoSuchElementException ex) 
					{
					Team newTeam = new Team();
					newTeam.setFantasyId2017(Integer.parseInt(csvRecord.get("id")));
					newTeam.setName(csvRecord.get("name"));
					newTeam.setCode(Integer.parseInt(csvRecord.get("code")));
					newTeam.setShort_name(csvRecord.get("short_name"));
					newTeam.setUnavailable(true);
					newTeam.setStrength(Integer.parseInt(csvRecord.get("strength")));
					newTeam.setPosition(Integer.parseInt(csvRecord.get("position")));
					newTeam.setPlayed(Integer.parseInt(csvRecord.get("played")));
					newTeam.setWin(Integer.parseInt(csvRecord.get("win")));
					newTeam.setLoss(Integer.parseInt(csvRecord.get("loss")));
					newTeam.setDraw(Integer.parseInt(csvRecord.get("draw")));
					newTeam.setPoints(Integer.parseInt(csvRecord.get("points")));
					newTeam.setLink_url(csvRecord.get("link_url"));
					newTeam.setStrength_overall_home(Integer.parseInt(csvRecord.get("strength_overall_home")));
					newTeam.setStrength_overall_away(Integer.parseInt(csvRecord.get("strength_overall_away")));
					newTeam.setStrength_attack_home(Integer.parseInt(csvRecord.get("strength_attack_home")));
					newTeam.setStrength_defence_home(Integer.parseInt(csvRecord.get("strength_defence_home")));
					newTeam.setStrength_attack_away(Integer.parseInt(csvRecord.get("strength_attack_away")));
					newTeam.setStrength_defence_away(Integer.parseInt(csvRecord.get("strength_defence_away")));
					teamService.addTeam(newTeam);
		        	}
        	}
        	teamService.saveTeams();
        } catch (IOException e) {
        	System.out.println("File not found.");
		}
	}
	
	
	public void processFixturesCSV(String filePath) throws IOException {
		
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
    	
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) 
        {
        	for (CSVRecord csvRecord : csvParser) {
        		
	        	int code = Integer.parseInt(csvRecord.get("id"));
				Fixture newFixture = new Fixture();
				newFixture.setSeason("2017/18");
				newFixture.setFantasyId(Integer.parseInt(csvRecord.get("id")));
				newFixture.setEvent(Integer.parseInt(csvRecord.get("event")));
				newFixture.setDeadlineTime(Date.from(Instant.parse(csvRecord.get("deadline_time"))));
				newFixture.setKickoffTime(Date.from(Instant.parse(csvRecord.get("kickoff_time"))));
				newFixture.setAwayTeam(teamService.getTeamByFantasyId2017(Integer.parseInt(csvRecord.get("team_a"))));
				newFixture.setHomeTeam(teamService.getTeamByFantasyId2017(Integer.parseInt(csvRecord.get("team_h"))));
				newFixture.setHomeTeamDifficulty(Integer.parseInt(csvRecord.get("team_h_difficulty")));
				newFixture.setAwayTeamDifficulty(Integer.parseInt(csvRecord.get("team_a_difficulty")));
				newFixture.setCode(Integer.parseInt(csvRecord.get("code")));
				newFixture.setHomeTeamScore(Integer.parseInt(csvRecord.get("team_h_score")));
				newFixture.setAwayTeamScore(Integer.parseInt(csvRecord.get("team_a_score")));
				newFixture.setFinished(new Boolean("1".equals(csvRecord.get("finished"))));
				fixtureService.addFixture(newFixture);
					
		  }
        	fixtureService.saveFixtures();
        }

	}
	
	
	public void processPlayerProfileCSV(String filePath) throws IOException {
		
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
    	
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) 
        {
        	for (CSVRecord csvRecord : csvParser) {
        		
                int code = Integer.parseInt(csvRecord.get("code"));
        		try {
        			Player player = playerService.getPlayerByCode(code);
        			player.getPlayerProfile().setFantasyId2017(Integer.parseInt(csvRecord.get("id")));
        			playerService.getHistorySeason(player, "2017/18").setTeam(teamService.getTeamByCode(Integer.parseInt(csvRecord.get("team_code"))));
        		}
        		catch (NoSuchElementException e) {
        			continue;
        			
        		}
		  }
        	playerService.savePlayers();
        }

	}
	

    public void processHistoryPerfomarnces(String filePath) throws IOException {
		
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
    
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
				try {
					Player player = playerService.getPlayerByFantasyIdAndSeason(Integer.parseInt(csvRecord.get("element")), "2017/18");
					HistorySeason historySeason = playerService.getHistorySeason(player, "2017/18");
					FullPerformance parsedPerformance = new FullPerformance();
    				parsedPerformance.setFixture(fixtureService.getFixtureByFantasyIdAndSeason(Integer.parseInt(csvRecord.get("fixture")), "2017/18"));
    				parsedPerformance.setWasHome(new Boolean("1".equals(csvRecord.get("was_home"))));
    				parsedPerformance.setRound(Integer.parseInt(csvRecord.get("round")));
    				parsedPerformance.setTotalPoints(Integer.parseInt(csvRecord.get("total_points")));
    				parsedPerformance.setValue(Integer.parseInt(csvRecord.get("value")));
    				parsedPerformance.setSelected(Integer.parseInt(csvRecord.get("selected")));
    				parsedPerformance.setTransfersIn(Integer.parseInt(csvRecord.get("transfers_in")));
    				parsedPerformance.setTransfersOut(Integer.parseInt(csvRecord.get("transfers_out")));
    				parsedPerformance.setLoanedIn(Integer.parseInt(csvRecord.get("loaned_in")));
    				parsedPerformance.setLoanedOut(Integer.parseInt(csvRecord.get("loaned_out")));
    				parsedPerformance.setMinutes(Integer.parseInt(csvRecord.get("minutes")));
    				parsedPerformance.setGoalsScored(Integer.parseInt(csvRecord.get("goals_scored")));
    				parsedPerformance.setAssists(Integer.parseInt(csvRecord.get("assists")));
    				parsedPerformance.setCleanSheets(Integer.parseInt(csvRecord.get("clean_sheets")));
    				parsedPerformance.setGoalsConceded(Integer.parseInt(csvRecord.get("goals_conceded")));
    				parsedPerformance.setOwnGoals(Integer.parseInt(csvRecord.get("own_goals")));
    				parsedPerformance.setPenaltiesSaved(Integer.parseInt(csvRecord.get("penalties_saved")));
    				parsedPerformance.setPenaltiesMissed(Integer.parseInt(csvRecord.get("penalties_missed")));
    				parsedPerformance.setYellowCards(Integer.parseInt(csvRecord.get("yellow_cards")));
    				parsedPerformance.setRedCards(Integer.parseInt(csvRecord.get("red_cards")));
    				parsedPerformance.setSaves(Integer.parseInt(csvRecord.get("saves")));
    				parsedPerformance.setBonus(Integer.parseInt(csvRecord.get("bonus")));
    				parsedPerformance.setBps(Integer.parseInt(csvRecord.get("bps")));
    				parsedPerformance.setInfluence(Float.parseFloat(csvRecord.get("influence")));
    				parsedPerformance.setCreativity(Float.parseFloat(csvRecord.get("creativity")));
    				parsedPerformance.setThreat(Float.parseFloat(csvRecord.get("threat")));
    				parsedPerformance.setIctIndex(Float.parseFloat(csvRecord.get("ict_index")));
    				parsedPerformance.setEaIndex(Integer.parseInt(csvRecord.get("ea_index")));
    				parsedPerformance.setOpenPlayCrosses(Integer.parseInt(csvRecord.get("open_play_crosses")));
    				parsedPerformance.setBigChancesCreated(Integer.parseInt(csvRecord.get("big_chances_created")));
    				parsedPerformance.setClearancesBlocksInterceptions(Integer.parseInt(csvRecord.get("clearances_blocks_interceptions")));
    				parsedPerformance.setRecoveries(Integer.parseInt(csvRecord.get("recoveries")));
    				parsedPerformance.setKeyPasses(Integer.parseInt(csvRecord.get("key_passes")));
    				parsedPerformance.setTackles(Integer.parseInt(csvRecord.get("tackles")));
    				parsedPerformance.setWinningGoals(Integer.parseInt(csvRecord.get("winning_goals")));
    				parsedPerformance.setAttemptedPasses(Integer.parseInt(csvRecord.get("attempted_passes")));
    				parsedPerformance.setCompletedPasses(Integer.parseInt(csvRecord.get("completed_passes")));
    				parsedPerformance.setPenaltiesConceded(Integer.parseInt(csvRecord.get("penalties_conceded")));
    				parsedPerformance.setBigChancesMissed(Integer.parseInt(csvRecord.get("big_chances_missed")));
    				parsedPerformance.setErrorsLeadingToGoal(Integer.parseInt(csvRecord.get("errors_leading_to_goal")));
    				parsedPerformance.setErrorsLeadingToGoalAttempt(Integer.parseInt(csvRecord.get("errors_leading_to_goal_attempt")));
    				parsedPerformance.setTackled(Integer.parseInt(csvRecord.get("tackled")));
    				parsedPerformance.setOffside(Integer.parseInt(csvRecord.get("offside")));
    				parsedPerformance.setTargetMissed(Integer.parseInt(csvRecord.get("target_missed")));
    				parsedPerformance.setFouls(Integer.parseInt(csvRecord.get("fouls")));
    				parsedPerformance.setDribbles(Integer.parseInt(csvRecord.get("dribbles")));
    				historySeason.addOrReplaceHistorySeasonPerformance(parsedPerformance);
				}
				catch (NoSuchElementException e) {
					continue;
				}
			}
		playerService.savePlayers();
        }
    }
                
}



