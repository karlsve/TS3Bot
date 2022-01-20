package de.karlsve.ts3.events;

import de.karlsve.ts3.client.Client;

public class ChannelMessageEvent extends MessageEvent {

    public final int receiverId;

    public ChannelMessageEvent(int invokerId, int receiverId, String message, Client client) {
        super("channel", invokerId, message, client);
        this.receiverId = receiverId;
    }
    
}
