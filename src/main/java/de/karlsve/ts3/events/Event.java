package de.karlsve.ts3.events;

public class Event {

    public final String name;

    public Event(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
