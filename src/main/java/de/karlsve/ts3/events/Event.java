package de.karlsve.ts3.events;

import java.util.Map;

import de.karlsve.ts3.ServerBot;

public abstract class Event {
	
	private static int count = 0;
	
	private ServerBot handle = null;
	private int id = 0;
	private String type = "";
	private Map<String, String> info = null;
	
	public Event(ServerBot handle, String type, Map<String, String> eventInfo) {
		this.handle = handle;
		this.id = Event.count++;
		this.type = type;
		this.info = eventInfo;
	}
	
	public ServerBot getHandle() {
		return this.handle;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Map<String, String> getInfo() {
		return this.info;
	}
	
	@Override
	public String toString() {
		return String.format("Event::id:%d::", this.id);
	}

}
