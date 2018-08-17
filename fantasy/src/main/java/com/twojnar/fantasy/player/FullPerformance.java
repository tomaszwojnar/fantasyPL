package com.twojnar.fantasy.player;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.twojnar.fantasy.fixture.Fixture;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FullPerformance extends Performance {
	
	private Fixture fixture;
	
	private Map<String, String> explain;
	
	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Map<String, String> getExplain() {
		return explain;
	}

	public void setExplain(Map<String, String> explain) {
		this.explain = explain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixture == null) ? 0 : fixture.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FullPerformance other = (FullPerformance) obj;
		if (fixture == null) {
			if (other.fixture != null)
				return false;
		} else if (!fixture.equals(other.fixture))
			return false;
		return true;
	}
}



