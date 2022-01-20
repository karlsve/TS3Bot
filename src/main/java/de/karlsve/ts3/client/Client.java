package de.karlsve.ts3.client;

public interface Client {
    
    public boolean connect();
    public void disconnect();
    public boolean sendPrivateMessage(int receiverId, String message);
    public boolean joinChannel(int receiverId, String password);
    public Object clientInfo(int clientId);

}
