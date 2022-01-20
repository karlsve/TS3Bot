package de.karlsve.ts3.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.TimeoutException;

import com.github.manevolent.ts3j.command.CommandException;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.identity.Identity;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.events.PrivateMessageEvent;
import de.karlsve.ts3.settings.Settings;

public class IdentityClient implements Client, TS3Listener {

    public final Identity identity;
    public final LocalTeamspeakClientSocket handle;

    public IdentityClient() throws GeneralSecurityException {
        this(10);
    }

    public IdentityClient(int securityLevel) throws GeneralSecurityException {
        this(LocalIdentity.generateNew(securityLevel));
    }

    public IdentityClient(Identity identity) {
        this.identity = identity;
        this.handle = new LocalTeamspeakClientSocket();
        this.handle.setIdentity(identity);
        this.handle.setNickname(Settings.getInstance().get("name", "ServerBot"));
    }

    @Override
    public boolean connect() {
        try {
            this.handle.addListener(this);
            this.handle.connect(Settings.getInstance().get("host", "localhost"), Settings.getInstance().get("password", null), Long.valueOf(Settings.getInstance().get("timeout", "30000")));
            this.handle.subscribeAll();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public void disconnect() {
        if(this.handle.isConnected()) {
            try {
                this.handle.disconnect();
                this.handle.close();
            } catch(Exception e) {
                Log.e(e);
            }
        }
    }

    @Override
    public void onTextMessage(TextMessageEvent event) {
        if(event.getInvokerId() == this.handle.getClientId()) { // Ignore own messages
            return;
        }
        switch(event.getTargetMode()) {
            case CLIENT:
                EventManager.getInstance().trigger(new PrivateMessageEvent(event.getInvokerId(), this.handle.getClientId(), event.getMessage(), this));
                break;
            case CHANNEL:
                // TODO: Implement
                break;
            case SERVER:
                // TODO: Implement
                break;
        }
    }

    @Override
    public boolean sendPrivateMessage(int receiverId, String message) {
        try {
            this.handle.sendPrivateMessage(receiverId, message);
        } catch (IOException | TimeoutException | InterruptedException | CommandException e) {
            Log.e(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean joinChannel(int receiverId, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object clientInfo(int clientId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
