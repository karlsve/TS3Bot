package de.karlsve.ts3.events;

import de.karlsve.ts3.Log;

public abstract class MessageListener<T extends MessageEvent> implements MatchingEventListener<T> {
	@Override
	public boolean matches(Event event) {
		Log.d("matching", event);
		return event instanceof MessageEvent;
	}
}
