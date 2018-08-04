package com.twojnar.fantasy.fixture;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

public class Fixture {
	
	private int code;
	
	private Date deadlineTime;
	
	private int event;
	
	private Boolean finished;
	
	private Boolean finishedProvisional;
	
	@Field("fantasyId")
	private int id;
	
	private Date kickoffTime;
	
	private int minutes;


}
