package de.karlsve.ts3.events;

import java.util.Vector;

import de.karlsve.ts3.Log;

public class EventManager {

	private static EventManager instance = null;

	public static EventManager getInstance() {
		if (EventManager.instance == null) {
			EventManager.instance = new EventManager();
		}
		return instance;
	}

	private Vector<EventListener<?>> listeners = new Vector<>();

	private EventManager() { }

	public void addListener(EventListener<?> listener) {
		this.listeners.add(listener);
	}

	public void removeListener(EventListener<?> listener) {
		this.listeners.remove(listener);
	}

	public <T extends Event> void trigger(T event) {
		Log.d("Event triggered: ", event);
		this.listeners.forEach(l -> {
			if(l instanceof MatchingEventListener && !((MatchingEventListener<?>) l).matches(event)) {
				return;
			}
			((EventListener<T>) l).onEvent(event);
		});
	}
}
