package de.karlsve.ts3.events;

import de.karlsve.ts3.client.Client;

public class ServerMessageEvent extends MessageEvent {

    public ServerMessageEvent(int invokerId, String message, Client client) {
        super("server", invokerId, message, client);
    }
    
}
