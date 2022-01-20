package de.karlsve.ts3.command;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.events.MessageListener;
import de.karlsve.ts3.events.PrivateMessageEvent;
import de.karlsve.ts3.events.PrivateMessageListener;

public class CommandManager {
	
	private static CommandManager instance;

	public static CommandManager getInstance() {
		if(CommandManager.instance == null) {
			CommandManager.instance = new CommandManager();
		}
		return CommandManager.instance;
	}

	private HashMap<Command<?>, MessageListener<?>> commands;

	private CommandManager() {
		Log.d("CommandService starting...");
		this.initCommands();
		Log.d("CommandService running...");
	}
	
	public List<Command<?>> getCommands() {
		return this.commands.keySet().stream().collect(Collectors.toList());
	}

	private void initCommands() {
		this.addCommand(new NameCommand());
		this.addCommand(new HelpCommand());
		//this.registerCommand(new ExecuteCommand(this.handle));
		this.addCommand(new ShutdownCommand());
		this.addCommand(new JoinCommand());
	}

	public void addCommand(Command<?> command) {
		if (command instanceof PrivateCommand) {
			EventManager.getInstance().addListener(new PrivateMessageListener() {
				@Override
				public void onEvent(PrivateMessageEvent event) {
					if (event.message.matches(command.getPattern())) {
						((PrivateCommand) command).onCommand(event);
					}
				}
			});
		}
	}

	public void removeCommand(Command<?> command) {
		if(this.commands.containsKey(command)) {
			EventManager.getInstance().removeListener(this.commands.get(command));
		}
	}

}
