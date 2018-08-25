package com.twojnar.fantasy.fixture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@EnableMongoRepositories
@Service
public class EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private FixtureService fixtureService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private FantasyStatus fantasyStatus;
	
	
	private List<Event> events = new ArrayList<Event>();
	
	
	public void updateFromDB() {
		this.events = eventRepository.findAll();
	}

	public void saveToDB() {
		eventRepository.saveAll(this.events);
	}
	
	public void updateEvents(List<Event> updatedEvents) {
		for (Event updatedEvent : updatedEvents) {
			this.events.stream()
				.filter(e -> e.equals(updatedEvent)).findFirst().ifPresentOrElse(
					x -> {
						updatedEvent.setId(x.getId());
						this.events.remove(x);
						this.events.add(updatedEvent);
					},
					() -> this.events.add(updatedEvent)
				);
		}
	}
	
	public void initialLoad(List<Event> events) {
		this.events.clear();
		for (Event updatedEvent : events) {
				this.events.add(updatedEvent);
		};
		eventRepository.deleteAll();
		this.saveToDB();
	}

	public List<Event> getEvents() {
		return this.events;
	}
	
	public Event getFixtureByFantasyIdAndSeason(int id, String season) {
		return this.events.stream()
		.filter(e -> e.getFantasy_id() == id && e.getSeason().equalsIgnoreCase(season)).collect(Collectors.toList()).get(0);
	}
}
