package de.karlsve.ts3.client;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import de.karlsve.ts3.events.ChannelMessageEvent;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.events.PrivateMessageEvent;
import de.karlsve.ts3.events.ServerMessageEvent;
import de.karlsve.ts3.settings.Settings;

public class ServerQueryClient implements Client, TS3Listener {

    private TS3Config config;
    private TS3Query query;
    private TS3Api api = null;

    public ServerQueryClient() {
        this.config = new TS3Config();
        this.config.setHost(Settings.getInstance().get("host", "localhost"));
        this.config.setQueryPort(Integer.parseInt(Settings.getInstance().get("port", "10011")));
        this.query = new TS3Query();
    }

    @Override
    public boolean connect() {
        this.query.connect();
        this.api = this.query.getApi();
        this.api.addTS3Listeners(this);
        this.api.login(Settings.getInstance().get("query_user", "serveradmin"), Settings.getInstance().get("query_password", "serveradmin"));
        this.api.selectVirtualServerById(1);
        String nickname = Settings.getInstance().get("name", "jarvis");
        if(!this.api.whoAmI().getNickname().equals(nickname)) {
            this.api.setNickname(nickname);
        }
        this.api.registerAllEvents();
        return true;
    }

    @Override
    public void disconnect() {
        this.query.exit();
        
    }

    @Override
    public boolean sendPrivateMessage(int receiverId, String message) {
        // TODO Auto-generated method stub
        return false;
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

    @Override
    public void onChannelCreate(ChannelCreateEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChannelDeleted(ChannelDeletedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChannelEdit(ChannelEditedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChannelMoved(ChannelMovedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onChannelPasswordChanged(ChannelPasswordChangedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientJoin(ClientJoinEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientLeave(ClientLeaveEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClientMoved(ClientMovedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onServerEdit(ServerEditedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onTextMessage(TextMessageEvent evt) {
        switch(evt.getTargetMode()) {
            case CHANNEL:
                EventManager.getInstance().trigger(new ChannelMessageEvent(evt.getInvokerId(), evt.getTargetClientId(), evt.getMessage(), this));
                break;
            case CLIENT:
                EventManager.getInstance().trigger(new PrivateMessageEvent(evt.getInvokerId(), evt.getTargetClientId(), evt.getMessage(), this));
                break;
            case SERVER:
                EventManager.getInstance().trigger(new ServerMessageEvent(evt.getInvokerId(), evt.getMessage(), this));
                break;
        }
    }
    
}
