package de.karlsve.ts3.client;

import de.karlsve.ts3.api.DynamicMap;

public interface Client {
    
    public void connect() throws Exception;
    public void disconnect();
    default public void sendPrivateMessage(int receiverId, String message) throws Exception {
        throw new UnsupportedOperationException();
    };
    default public void sendServerMessage(String message) throws Exception {
        throw new UnsupportedOperationException();
    };
    default public void joinChannel(int channelId, String password) throws Exception {
        throw new UnsupportedOperationException();
    };
    public DynamicMap<String> clientInfo(int clientId);

}
