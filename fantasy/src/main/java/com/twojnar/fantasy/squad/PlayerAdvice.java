package com.twojnar.fantasy.squad;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.twojnar.fantasy.common.View;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonView(View.PublicGeneral.class)
public class PlayerAdvice implements IAdvice {
	
	private List<Swap> swaps = new ArrayList<Swap>();

	public List<Swap> getSwaps() {
		return swaps;
	}

	public void setSwaps(List<Swap> swaps) {
		this.swaps = swaps;
	}
	
	public void addSwap(Swap swap) {
		this.swaps.add(swap);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((swaps == null) ? 0 : swaps.hashCode());
		return result;
	}
	
	public PlayerAdvice sort() {
		this.swaps.sort(Comparator.comparing(Swap::getBenefit, Comparator.nullsLast(Comparator.reverseOrder())));
		return this;
	}
	
	public PlayerAdvice limit(int limit) {
		this.setSwaps(this.swaps.stream().limit(limit).collect(Collectors.toList()));
		return this;
	}
}
