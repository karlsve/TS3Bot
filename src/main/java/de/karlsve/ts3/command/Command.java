package de.karlsve.ts3.command;

import java.util.regex.Pattern;

import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.ServerBot;

public abstract class Command implements ICommand {
	
	private Pattern pattern = Pattern.compile("");
	private ServerBot handle = null;

	public Command(ServerBot handle, String pattern) {
		this.handle = handle;
		this.pattern = Pattern.compile("^" + pattern + "$");
	}
	
	public Pattern getPattern() {
		return this.pattern;
	}
	
	public ServerBot getHandle() {
		return this.handle;
	}

	@Override
	public void onMessageReceived(TextMessageEvent event) {
		if (this.getPattern().matcher(event.getMessage()).matches()) {
			this.onTrigger(event);
		}
	}

	@Override
	public void onMessageSent(TextMessageEvent event) {
	}

	public abstract void onTrigger(TextMessageEvent event);

}
