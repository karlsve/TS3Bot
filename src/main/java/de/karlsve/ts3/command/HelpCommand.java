package de.karlsve.ts3.command;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageEvent;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;
import de.stefan1200.jts3serverquery.TS3ServerQueryException;

public class HelpCommand extends PrivateCommand {

	public HelpCommand(ServerBot handle) {
		super(handle, "\\?");
	}

	@Override
	protected void onTrigger(MessageEvent event) {
		String message = String.format("List of commands:%n");
		for (Command command : this.getHandle().getCommandManager().getCommands()) {
			if (command instanceof PrivateCommand) {
				message += command.getPattern() + "\n";
			}
		}
		try {
			this.getHandle().getQuery().sendTextMessage(event.getInvokerId(),
					JTS3ServerQuery.TEXTMESSAGE_TARGET_CLIENT, message);
		} catch (TS3ServerQueryException e) {
			e.printStackTrace();
		}
	}

}
