package com.twojnar.taskRunner;

import java.io.IOException;

public interface TaskDefinition {
	
	public void updateAll() throws IOException;
	
	public void initialLoad() throws IOException;

}
