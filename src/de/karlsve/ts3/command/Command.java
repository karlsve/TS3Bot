package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;
import de.karlsve.ts3.events.MessageListener;

public abstract class Command implements MessageListener {
	
	private String pattern = "";
	private ServerBot handle = null;
	
	public Command(ServerBot handle, String pattern) {
		this.handle = handle;
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return this.pattern;
	}
	
	public ServerBot getHandle() {
		return this.handle;
	}

	@Override
	public void onMessageReceived(MessageEvent event) {
		if (event.getMessage().matches(this.getPattern())) {
			this.onTrigger(event);
		}
	}

	@Override
	public void onMessageSent(MessageEvent event) {
	}

	protected abstract void onTrigger(MessageEvent event);

}
