package de.karlsve.ts3.events;

public interface MessageListener {
	int getTargetMode();
	void onMessageReceived(MessageEvent event);
	void onMessageSent(MessageEvent event);
}
