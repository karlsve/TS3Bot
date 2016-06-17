package de.karlsve.ts3.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;
import de.stefan1200.jts3serverquery.TS3ServerQueryException;
import de.stefan1200.jts3serverquery.TeamspeakActionListener;

public class EventManager implements TeamspeakActionListener {
	
	private ServerBot handle;
	
	public static final String NOTIFY_TEXT = "notifytextmessage";
	public static final String NOTIFY_ENTER = "notifycliententerview";
	public static final String NOTIFY_LEFT = "notifyclientleftview";
	
	private List<MessageListener> messageListener = new ArrayList<>();
	private List<ClientListener> clientListener = new ArrayList<>();

	public EventManager(ServerBot handle) {
		this.handle = handle;
		this.handle.getQuery().setTeamspeakActionListener(this);
	}

	public void init() {
		Log.d("EventManager starting...");
		try {
			this.handle.getQuery().addEventNotify(JTS3ServerQuery.EVENT_MODE_TEXTPRIVATE, 0);
			this.handle.getQuery().addEventNotify(JTS3ServerQuery.EVENT_MODE_SERVER, 0);
			this.handle.getQuery().addEventNotify(JTS3ServerQuery.EVENT_MODE_TEXTSERVER, 0);
			/** TODO Rewrite to proper functioning event listening on every channel
			for(Map<String, String> channel : this.handle.getQuery().getList(JTS3ServerQuery.LISTMODE_CHANNELLIST)) {
				int cid = Integer.valueOf(channel.get("cid"));
				this.handle.getQuery().addEventNotify(JTS3ServerQuery.EVENT_MODE_TEXTCHANNEL, cid);
				this.handle.getQuery().addEventNotify(JTS3ServerQuery.EVENT_MODE_CHANNEL, cid);
			}
			**/
			Log.d("EventManager running...");
		} catch (TS3ServerQueryException e) {
			Log.e(new Exception("EventManager error..."));
			Log.e(e);
		}
	}
	
	synchronized public List<MessageListener> getMessageListener() {
		return this.messageListener;
	}
	
	public void addMessageListener(MessageListener listener) {
		this.getMessageListener().add(listener);
	}
	
	synchronized public List<ClientListener> getClientListener() {
		return this.clientListener;
	}
	
	public void addClientListener(ClientListener listener) {
		this.getClientListener().add(listener);
	}

	@Override
	public void teamspeakActionPerformed(String eventType, HashMap<String, String> eventInfo) {
		Log.d(String.format("EventTriggered: %s", eventType));
		switch (eventType) {
		case NOTIFY_TEXT:
			this.triggerMessageEvent(new MessageEvent(this.handle, eventType, eventInfo));
			break;
		case NOTIFY_ENTER:
			this.triggerClientEvent(new ClientEnterEvent(this.handle, eventType, eventInfo));
			break;
		case NOTIFY_LEFT:
			this.triggerClientEvent(new ClientLeftEvent(this.handle, eventType, eventInfo));
			break;
		default:
			Log.d(String.format("%s: %s", eventType, eventInfo));
			break;
		}
	}

	private void triggerClientEvent(ClientEvent clientEvent) {
		for(ClientListener listener : this.getClientListener()) {
			listener.onTrigger(clientEvent);
		}
	}

	private void triggerMessageEvent(MessageEvent event) {
		for(MessageListener listener : this.getMessageListener()) {
			if(listener.getTargetMode() == event.getTargetMode()) {
				if(event.isReceived()) {
					listener.onMessageReceived(event);
				} else {
					listener.onMessageSent(event);
				}
			}
		}
	}

}
