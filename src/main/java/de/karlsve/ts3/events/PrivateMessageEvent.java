package de.karlsve.ts3.events;

import de.karlsve.ts3.client.Client;

public class PrivateMessageEvent extends MessageEvent {

    public final int receiverId;

    public PrivateMessageEvent(int invokerId, int receiverId, String message, Client client) {
        super("private", invokerId, message, client);
        this.receiverId = receiverId;
    }
    
}
