package de.karlsve.ts3.events;

import java.util.Map;

import de.karlsve.ts3.ServerBot;

public class ClientEnterEvent extends ClientEvent {

	public ClientEnterEvent(ServerBot handle, String type, Map<String, String> eventInfo) {
		super(handle, type, eventInfo);
	}

}
