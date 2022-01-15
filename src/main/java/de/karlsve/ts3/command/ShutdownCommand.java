package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.ServerBot;

public class ShutdownCommand extends PrivateCommand {

	public ShutdownCommand(ServerBot handle) {
		super(handle, "\\!shutdown");
	}

	@Override
	public void onTrigger(TextMessageEvent event) {
		this.getHandle().shutdown();
	}

}
