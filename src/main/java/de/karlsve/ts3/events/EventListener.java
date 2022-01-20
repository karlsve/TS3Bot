package de.karlsve.ts3.events;

public abstract interface EventListener<T extends Event> {

    public void onEvent(T event);
    
}
