package de.karlsve.ts3.events;

public abstract interface MatchingEventListener<T extends Event> extends EventListener<T> {
    public boolean matches(Event event);
    public void onEvent(T event);
}
