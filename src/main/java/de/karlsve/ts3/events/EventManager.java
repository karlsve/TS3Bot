package de.karlsve.ts3.events;

import java.util.Vector;

import com.github.manevolent.ts3j.event.ClientJoinEvent;
import com.github.manevolent.ts3j.event.ClientLeaveEvent;
import com.github.manevolent.ts3j.event.ClientMovedEvent;
import com.github.manevolent.ts3j.event.TS3Listener;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;

public class EventManager implements TS3Listener {
	
	public static final String NOTIFY_TEXT = "notifytextmessage";
	public static final String NOTIFY_ENTER = "notifycliententerview";
	public static final String NOTIFY_LEFT = "notifyclientleftview";

	private LocalTeamspeakClientSocket handle;
	
	private Vector<MessageListener> messageListener = new Vector<>();
	private Vector<ClientListener> clientListener = new Vector<>();

	public EventManager(LocalTeamspeakClientSocket handle) {
		this.handle = handle;
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
	public void onTextMessage(TextMessageEvent event) {
		if(event.getInvokerId() == this.handle.getClientId()) {
			return;
		}
		this.messageListener.stream().filter(listener -> listener.getTargetMode() == event.getTargetMode()).forEach(listener -> {
			if (event.getTargetClientId() == this.handle.getClientId()) {
				listener.onMessageReceived(event);
			} else {
				listener.onMessageSent(event);
			}
		});
	}

	@Override
	public void onClientLeave(ClientLeaveEvent event) {
		this.clientListener.stream().forEach(listener -> listener.onLeave(event));
	}

	@Override
	public void onClientJoin(ClientJoinEvent event) {
		this.clientListener.stream().forEach(listener -> listener.onJoin(event));
	}

	@Override
	public void onClientMoved(ClientMovedEvent event) {
		this.clientListener.stream().forEach(listener -> listener.onMoved(event));
	}

}
