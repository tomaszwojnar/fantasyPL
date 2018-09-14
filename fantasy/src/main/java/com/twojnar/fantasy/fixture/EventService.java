package com.twojnar.fantasy.fixture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import com.twojnar.fantasy.common.FantasyStatus;
import com.twojnar.fantasy.common.ListUtil;
import com.twojnar.fantasy.player.Player;
import com.twojnar.fantasy.team.Team;
import com.twojnar.fantasy.team.TeamService;

@EnableMongoRepositories
@Service
public class EventService {
	
	static final Logger logger = LoggerFactory.getLogger(EventService.class);
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private FantasyStatus fantasyStatus;
	
	
	private List<Event> events = new ArrayList<Event>();
	
	
	public void updateFromDB() {
		this.events = eventRepository.findAll();
	}
	
	public void saveToDB() {
		eventRepository.saveAll(this.events);
	}
	
	/**
	 * Method updates the list of existing events using the list of updatedEvents provided. If an event does not exist, it is created and added.
	 * The method does not persist the data.
	 * 
	 * @param updatedEvents - List of events to be
	 * @return void
	 */
	
	
	public void updateEvents(List<Event> updatedEvents) {
		for (Event updatedEvent : updatedEvents) {
			Optional <Event> optionalEvent = this.events.stream().filter(e ->  e.equals(updatedEvent)).findFirst();
				if (optionalEvent.isPresent()) {
					Event event = optionalEvent.get();
					updatedEvent.setId(event.getId());
					this.events.remove(event);
					this.events.add(updatedEvent);
				}
				else this.events.add(updatedEvent);
		}
	}
	
	/**
	 * Method clears the list of existing events, deletes the events in the repository, adds the events from the list and persists them.
	 * 
	 * @param updatedEvents - List of events to be
	 * @return void
	 */
	
	public void initialLoad(List<Event> events) {
		this.events.clear();
		eventRepository.deleteAll();
		for (Event updatedEvent : events) {
				this.events.add(updatedEvent);
		};
		this.saveToDB();
	}

	public List<Event> getEvents() {
		return this.events;
	}
	
	/**
	 * Method returns an event by FantasyId and Season.
	 * 
	 * @param id - fantasyId for the Event
	 * @param seaons - season in "2018/19" format
	 * @return void
	 */
	
	public Event getEventByFantasyIdAndSeason(int id, String season) {
		List<Event> events = this.events.stream()
					.filter(e -> e.getFantasy_id() == id && e.getSeason().equalsIgnoreCase(season))
					.collect(Collectors.toList());
		return ListUtil.getSingleFromList(events);
	}
	
	/**
	 * Method returns an event in the current season by FantasyId.
	 * 
	 * @param updatedEvents - List of events to be
	 * @return void
	 */
	
	public Event getEventByFantasyId(int id) {
		return this.getEventByFantasyIdAndSeason(id, fantasyStatus.getCurrentSeason());
	}
}
