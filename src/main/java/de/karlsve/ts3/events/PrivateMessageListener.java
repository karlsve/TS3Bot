package de.karlsve.ts3.events;

public abstract class PrivateMessageListener extends MessageListener<PrivateMessageEvent> {

    public boolean matches(Event event) {
        return event instanceof PrivateMessageEvent;
    }

}
