package de.karlsve.ts3.events;

import java.util.HashMap;
import java.util.Vector;

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
	
	private Vector<MessageListener> messageListener = new Vector<>();
	private Vector<ClientListener> clientListener = new Vector<>();

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
			Log.d("EventManager running...");
		} catch (TS3ServerQueryException e) {
			Log.e(new Exception("EventManager error..."));
			Log.e(e);
		}
	}
	
	public void addMessageListener(MessageListener listener) {
		this.messageListener.add(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		this.messageListener.remove(listener);
	}
	
	public void addClientListener(ClientListener listener) {
		this.clientListener.add(listener);
	}

	public void removeClientListener(ClientListener listener) {
		this.clientListener.remove(listener);
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
		for(ClientListener listener : this.clientListener) {
			listener.onTrigger(clientEvent);
		}
	}

	private void triggerMessageEvent(MessageEvent event) {
		this.messageListener.stream().filter(listener -> listener.getTargetMode() == event.getTargetMode()).forEach(listener -> {
			if (event.isReceived()) {
				listener.onMessageReceived(event);
			} else {
				listener.onMessageSent(event);
			}
		});
	}

}
