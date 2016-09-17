package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;

public abstract class GlobalCommand extends Command {

	public GlobalCommand(ServerBot handle, String pattern) {
		super(handle, pattern);
	}

	@Override
	public int getTargetMode() {
		return MessageEvent.TARGET_MODE_SERVER;
	}

}
