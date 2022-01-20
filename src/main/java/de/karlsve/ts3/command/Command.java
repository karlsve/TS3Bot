package de.karlsve.ts3.command;

import de.karlsve.ts3.events.MessageEvent;

public interface Command<T extends MessageEvent> {
	
	public void onCommand(T event);
	public String getPattern();
	
}
