package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;

public abstract class PrivateCommand extends Command {

	public PrivateCommand(ServerBot handle, String pattern) {
		super(handle, pattern);
	}

	@Override
	public int getTargetMode() {
		return MessageEvent.TARGET_MODE_PRIVATE;
	}
	
}
