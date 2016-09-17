package de.karlsve.ts3.events;

import java.util.Map;

import de.karlsve.ts3.ServerBot;

public class ClientLeftEvent extends ClientEvent {

	public ClientLeftEvent(ServerBot handle, String type, Map<String, String> eventInfo) {
		super(handle, type, eventInfo);
	}

}
