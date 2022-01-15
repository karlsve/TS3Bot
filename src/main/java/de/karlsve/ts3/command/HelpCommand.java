package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.ServerBot;

public class HelpCommand extends PrivateCommand {

	public HelpCommand(ServerBot handle) {
		super(handle, "\\!\\?|\\!help");
	}

	@Override
	public void onTrigger(TextMessageEvent event) {
		String message = String.format("List of commands:%n");
		for (ICommand command : this.getHandle().getCommandManager().getCommands()) {
			if (command instanceof PrivateCommand) {
				message += command.getPattern() + "\n";
			}
		}
		try {
			this.getHandle().getClient().sendPrivateMessage(event.getInvokerId(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
