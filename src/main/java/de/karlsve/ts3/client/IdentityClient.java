package de.karlsve.ts3.client;

import java.io.File;
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
import de.karlsve.ts3.api.DynamicMap;
import de.karlsve.ts3.events.ChannelMessageEvent;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.events.PrivateMessageEvent;
import de.karlsve.ts3.events.ServerMessageEvent;
import de.karlsve.ts3.settings.Settings;

public class IdentityClient implements Client, TS3Listener {

    public final Identity identity;
    public final LocalTeamspeakClientSocket handle;

    public IdentityClient(File file) throws IOException, GeneralSecurityException {
        this(file.exists() ? LocalIdentity.read(file) : LocalIdentity.generateNew(10));
        ((LocalIdentity) this.identity).save(file);
    }

    public IdentityClient() throws GeneralSecurityException, IOException {
        this(new File(Settings.getInstance().get("identity_file", ".identity")));
    }

    public IdentityClient(int securityLevel) throws GeneralSecurityException {
        this(LocalIdentity.generateNew(securityLevel));
    }

    public IdentityClient(Identity identity) {
        this.identity = identity;
        this.handle = new LocalTeamspeakClientSocket();
        this.handle.setIdentity(identity);
        this.handle.setNickname(Settings.getInstance().get("name", "jarvis"));
    }

    @Override
    public void connect() throws IOException, TimeoutException, CommandException, InterruptedException {
        this.handle.addListener(this);
        String host = Settings.getInstance().get("host", "localhost") + (Settings.getInstance().get("port") != null ? ":" + Settings.getInstance().get("port") : "");
        this.handle.connect(host, Settings.getInstance().get("password", null), Settings.getInstance().get("timeout", 30000L));
        this.handle.subscribeAll();
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
                EventManager.getInstance().trigger(new PrivateMessageEvent(event.getInvokerId(), event.getTargetClientId(), event.getMessage(), this));
                break;
            case CHANNEL:
                EventManager.getInstance().trigger(new ChannelMessageEvent(event.getInvokerId(), event.getTargetClientId(), event.getMessage(), this));
                break;
            case SERVER:
                EventManager.getInstance().trigger(new ServerMessageEvent(event.getInvokerId(), event.getMessage(), this));
                break;
        }
    }

    @Override
    public void sendPrivateMessage(int receiverId, String message) throws IOException, InterruptedException, CommandException, InterruptedException, TimeoutException {
        this.handle.sendPrivateMessage(receiverId, message);
    }

    @Override
    public void joinChannel(int channelId, String password) throws InterruptedException, TimeoutException, IOException, CommandException {
        this.handle.joinChannel(channelId, password);
    }

    @Override
    public DynamicMap<String> clientInfo(int clientId) {
        try {
            DynamicMap<String> info = new DynamicMap<>();
            com.github.manevolent.ts3j.api.Client cinfo = this.handle.getClientInfo(clientId);
            info.put("id", cinfo.getId());
            info.put("channel_id", cinfo.getChannelId());
            info.put("away_message", cinfo.getAwayMessage());
            info.put("nickname", cinfo.getNickname());
            return info;
        } catch (IOException | TimeoutException | InterruptedException | CommandException e) {
            return null;
        }
    }
    
}
