package com.twojnar.fantasy.common;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

@Service
public class ListUtil {

	public static <T> T getSingleFromList(List<T> list) {
		switch (list.size()) {
		case 1: return list.get(0);
		case 0: throw new NoSuchElementException("1 element was expected in the list but was not found");
		default: throw new ApplicationException("Multiple elements were found on the list although 1 was expected");
	}
	}

}