package de.karlsve.ts3.command;

import de.karlsve.ts3.events.ServerMessageEvent;
import de.karlsve.ts3.settings.Settings;

public class NameCommand extends ServerCommand {

	public String getPattern() {
		return Settings.getInstance().get("name");
	}

	@Override
	public void onCommand(ServerMessageEvent event) {
		try {
			event.client.sendPrivateMessage(event.invokerId, "How can I help you?");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
