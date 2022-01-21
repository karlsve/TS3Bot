package de.karlsve.ts3.command;

import de.karlsve.ts3.events.PrivateMessageEvent;

public class HelpCommand extends PrivateCommand {

	@Override
	public String getPattern() {
		return "\\!\\?|\\!help";
	}

	@Override
	public void onCommand(PrivateMessageEvent message) {
		String response = String.format("List of commands:%n");
		for (Command<?> command : CommandManager.getInstance().getCommands()) {
			if (command instanceof PrivateCommand) {
				response += command.getPattern() + "\n";
			}
		}
		try {
			message.client.sendPrivateMessage(message.invokerId, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
