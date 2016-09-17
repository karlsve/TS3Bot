package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;
import de.stefan1200.jts3serverquery.TS3ServerQueryException;

public class NameCommand extends GlobalCommand {
	
	public NameCommand(ServerBot handle) {
		super(handle, "^!"+handle.getQuery().getCurrentQueryClientName()+"$");
	}

	@Override
	protected void onTrigger(MessageEvent event) {
		try {
			this.getHandle().getQuery().sendTextMessage(event.getInvokerId(), JTS3ServerQuery.TEXTMESSAGE_TARGET_CLIENT, "How can I help you?");
		} catch (TS3ServerQueryException e) {
			e.printStackTrace();
		}
	}

}
