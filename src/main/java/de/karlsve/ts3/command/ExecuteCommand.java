package de.karlsve.ts3.command;

import java.util.Map;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;
import de.stefan1200.jts3serverquery.TS3ServerQueryException;

public class ExecuteCommand extends PrivateCommand {

	public ExecuteCommand(ServerBot handle) {
		super(handle, "^!execute .*?$");
	}

	@Override
	protected void onTrigger(MessageEvent event) {
		String query = event.getMessage().replaceFirst("^!execute ", "");
		Map<String, String> response = event.getHandle().getQuery().doCommand(query);
		try {
			event.getHandle().getQuery().sendTextMessage(event.getInvokerId(), MessageEvent.TARGET_MODE_PRIVATE,
					response.toString());
		} catch (TS3ServerQueryException e) {
			e.printStackTrace();
		}
	}

}
