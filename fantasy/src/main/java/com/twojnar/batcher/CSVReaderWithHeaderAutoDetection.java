package com.twojnar.batcher;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twojnar.fantasy.fixture.FixtureService;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.player.PlayerService;
import com.twojnar.fantasy.player.SimplifiedPerformance;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class CSVReaderWithHeaderAutoDetection {
	
	@Autowired
	PlayerService playerService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	FixtureService fixtureService;

    public void process(String filePath) throws IOException {
		teamService.updateFromDB();
		fixtureService.updateFromDB();
		playerService.updateFromDB();
    	
    	Map<String, String> mapa = new HashMap<String, String>();
    	
    	mapa.put("1", "Arsenal");
    	mapa.put("2", "Bournemouth");
    	mapa.put("3", "Brighton");
    	mapa.put("4", "Burnley");
    	mapa.put("5", "Chelsea");
    	mapa.put("6", "Crystal Palace");
    	mapa.put("7", "Everton");
    	mapa.put("8", "Huddersfield");
    	mapa.put("9", "Leicester");
    	mapa.put("10", "Liverpool");
    	mapa.put("11", "Man City");
    	mapa.put("12", "Man Utd");
    	mapa.put("13", "Newcastle");
    	mapa.put("14", "Southampton");
    	mapa.put("15", "Stoke");
    	mapa.put("16", "Swansea");
    	mapa.put("17", "Spurs");
    	mapa.put("18", "Watford");
    	mapa.put("19", "West Brom");
    	mapa.put("20", "West Ham");
    	
    	
        try (
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        ) {
        	int ticker = 0;
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String firstName = csvRecord.get("first_name");
                String lastName = csvRecord.get("second_name");
				Optional <Player> player = playerService.getPlayers().stream().filter(x -> x.getPlayerProfile().getLastName().equalsIgnoreCase(lastName)).findFirst();
				try {
					player.get().
					getHistorySeasons().stream().filter(z -> z.getSeasonName().equalsIgnoreCase("2017/18")).findFirst().ifPresent(q -> {
    				for (int x = 1; x <39; x++) {
    					String gw = "GW " + x+ ": ";
        				SimplifiedPerformance gameweek = new SimplifiedPerformance();
        				gameweek.setRound(x);
        				gameweek.setTotalPoints(Integer.parseInt(csvRecord.get(gw + "total_points")));
        				gameweek.setValue(Integer.parseInt(csvRecord.get(gw + "value")));
        				gameweek.setSelected(Integer.parseInt(csvRecord.get(gw + "selected")));
        				gameweek.setTransfersIn(Integer.parseInt(csvRecord.get(gw + "transfers_in")));
        				gameweek.setTransfersOut(Integer.parseInt(csvRecord.get(gw + "transfers_out")));
        				gameweek.setLoanedIn(Integer.parseInt(csvRecord.get(gw + "transfers_in")));
        				gameweek.setLoanedOut(Integer.parseInt(csvRecord.get(gw + "transfers_out")));
        				gameweek.setMinutes(Integer.parseInt(csvRecord.get(gw + "minutes")));
        				gameweek.setGoalsScored(Integer.parseInt(csvRecord.get(gw + "goals_scored")));
        				gameweek.setAssists(Integer.parseInt(csvRecord.get(gw + "assists")));
        				gameweek.setCleanSheets(Integer.parseInt(csvRecord.get(gw + "clean_sheets")));
        				gameweek.setGoalsConceded(Integer.parseInt(csvRecord.get(gw + "goals_conceded")));
        				gameweek.setOwnGoals(Integer.parseInt(csvRecord.get(gw + "own_goals")));
        				gameweek.setPenaltiesSaved(Integer.parseInt(csvRecord.get(gw + "penalties_saved")));
        				gameweek.setPenaltiesMissed(Integer.parseInt(csvRecord.get(gw + "penalties_missed")));
        				gameweek.setYellowCards(Integer.parseInt(csvRecord.get(gw + "yellow_cards")));
        				gameweek.setRedCards(Integer.parseInt(csvRecord.get(gw + "red_cards")));
        				gameweek.setSaves(Integer.parseInt(csvRecord.get(gw + "saves")));
        				gameweek.setBonus(Integer.parseInt(csvRecord.get(gw + "bonus")));
        				gameweek.setBps(Integer.parseInt(csvRecord.get(gw + "bps")));
        				gameweek.setInfluence(Float.parseFloat(csvRecord.get(gw + "influence")));
        				gameweek.setCreativity(Float.parseFloat(csvRecord.get(gw + "creativity")));
        				gameweek.setThreat(Float.parseFloat(csvRecord.get(gw + "threat")));
        				gameweek.setIctIndex(Float.parseFloat(csvRecord.get(gw + "ict_index")));
        				gameweek.setEaIndex(Integer.parseInt(csvRecord.get(gw + "ea_index")));
        				gameweek.setOpenPlayCrosses(Integer.parseInt(csvRecord.get(gw + "open_play_crosses")));
        				gameweek.setBigChancesCreated(Integer.parseInt(csvRecord.get(gw + "big_chances_created")));
        				gameweek.setClearancesBlocksInterceptions(Integer.parseInt(csvRecord.get(gw + "clearances_blocks_interceptions")));
        				gameweek.setRecoveries(Integer.parseInt(csvRecord.get(gw + "recoveries")));
        				gameweek.setKeyPasses(Integer.parseInt(csvRecord.get(gw + "key_passes")));
        				gameweek.setTackles(Integer.parseInt(csvRecord.get(gw + "tackles")));
        				gameweek.setWinningGoals(Integer.parseInt(csvRecord.get(gw + "winning_goals")));
        				gameweek.setAttemptedPasses(Integer.parseInt(csvRecord.get(gw + "attempted_passes")));
        				gameweek.setCompletedPasses(Integer.parseInt(csvRecord.get(gw + "completed_passes")));
        				gameweek.setPenaltiesConceded(Integer.parseInt(csvRecord.get(gw + "penalties_conceded")));
        				gameweek.setBigChancesMissed(Integer.parseInt(csvRecord.get(gw + "big_chances_missed")));
        				gameweek.setErrorsLeadingToGoal(Integer.parseInt(csvRecord.get(gw + "errors_leading_to_goal")));
        				gameweek.setErrorsLeadingToGoalAttempt(Integer.parseInt(csvRecord.get(gw + "errors_leading_to_goal_attempt")));
        				gameweek.setTackled(Integer.parseInt(csvRecord.get(gw + "tackled")));
        				gameweek.setOffside(Integer.parseInt(csvRecord.get(gw + "offside")));
        				gameweek.setTargetMissed(Integer.parseInt(csvRecord.get(gw + "target_missed")));
        				gameweek.setFouls(Integer.parseInt(csvRecord.get(gw + "fouls")));
        				gameweek.setDribbles(Integer.parseInt(csvRecord.get(gw + "dribbles")));
        				gameweek.setOpponentTeam(mapa.get(csvRecord.get(gw + "opponent_team")));
        				gameweek.setWasHome(Boolean.parseBoolean(csvRecord.get(gw + "was_home")));
        				if (gameweek.getWasHome()) {
        					gameweek.setTeamHomeScore(Integer.parseInt(csvRecord.get(gw + "team_goals")));
        					gameweek.setTeamAwayScore(Integer.parseInt(csvRecord.get(gw + "opponent_goals")));
        				}
        				else {
        					gameweek.setTeamHomeScore(Integer.parseInt(csvRecord.get(gw + "opponent_goals")));
        					gameweek.setTeamAwayScore(Integer.parseInt(csvRecord.get(gw + "team_goals")));
        				}
        				gameweek.setOpponentStrength(this.getOpponentTeamStrength(gameweek.getOpponentTeam(), gameweek.getWasHome()));
    				q.addHistorySeasonPerformance(gameweek);
    				}
    			}
			);

			}
			catch (NoSuchElementException ex) {
				     continue;
			}
			System.out.println(lastName + "done");
            }
			playerService.savePlayers();
			System.out.println("Saving Done");
        }
    }
            
	public int getOpponentTeamStrength(String opponentTeam, Boolean wasHome) {
		int opponentStrength = 0;
		Team opponent = teamService.findByName(opponentTeam);
		if (opponent != null) {
			opponentStrength = (wasHome) ? opponent.getStrength_overall_home() : opponent.getStrength_overall_away();
		}
		else opponentStrength = 1000;
		return opponentStrength;
	}
           
}



