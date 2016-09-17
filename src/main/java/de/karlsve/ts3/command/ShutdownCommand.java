package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;

public class ShutdownCommand extends PrivateCommand {

	public ShutdownCommand(ServerBot handle) {
		super(handle, "^!shutdown$");
	}

	@Override
	protected void onTrigger(MessageEvent event) {
		event.getHandle().shutdown();
	}

}
