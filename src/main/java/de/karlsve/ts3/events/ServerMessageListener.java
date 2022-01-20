package de.karlsve.ts3.events;

public abstract class ServerMessageListener extends MessageListener<ServerMessageEvent> {

    public boolean matches(Event event) {
        return event instanceof ServerMessageEvent;
    }

}
