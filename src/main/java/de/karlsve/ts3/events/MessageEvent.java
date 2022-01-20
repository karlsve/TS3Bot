package de.karlsve.ts3.events;

import de.karlsve.ts3.client.Client;

public abstract class MessageEvent extends Event {

    public final int invokerId;
    public final String message;
    public final Client client;

    public MessageEvent(String type, int invokerId, String message, Client client) {
        super("message."+type);
        this.message = message;
        this.invokerId = invokerId;
        this.client = client;
    }

    @Override
    public String toString() {
        return super.toString() + ": " + this.message;
    }
    
}
