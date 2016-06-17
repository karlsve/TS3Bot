package de.karlsve.ts3.events;

public interface MessageListener {
	public int getTargetMode();
	public void onMessageReceived(MessageEvent event);
	public void onMessageSent(MessageEvent event);
}
