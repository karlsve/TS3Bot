package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.PrivateMessageEvent;

public class ShutdownCommand extends PrivateCommand {

	public String getPattern() {
		return "^\\!shutdown$";
	}

	@Override
	public void onCommand(PrivateMessageEvent event) {
		ServerBot.getInstance().shutdown();
	}

}
