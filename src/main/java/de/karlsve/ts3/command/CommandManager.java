package de.karlsve.ts3.command;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;

public class CommandManager {
	
	private ServerBot handle = null;
	private List<ICommand> commands = new ArrayList<>();

	public CommandManager(ServerBot handle) {
		this.handle = handle;
		this.init();
	}
	
	public List<ICommand> getCommands() {
		return this.commands;
	}

	private void init() {
		Log.d("CommandService starting...");
		this.initCommands();
		Log.d("CommandService running...");
	}

	private void initCommands() {
		this.registerCommand(NameCommand.class);
		this.registerCommand(HelpCommand.class);
		//this.registerCommand(new ExecuteCommand(this.handle));
		this.registerCommand(ShutdownCommand.class);
	}

	public <T extends ICommand> void registerCommand(Class<T> command) {
		try {
			ICommand inst = command.getDeclaredConstructor(ServerBot.class).newInstance(this.handle);
			this.handle.getEventManager().addMessageListener(inst);
			this.getCommands().add(inst);
		} catch (Exception e) {
			Log.d("Failed to register command: " + command.getName());
			Log.e(e);
			e.printStackTrace();
		}
	}

	public <T extends Command> void unregisterCommand(Class<T> command) {
		this.getCommands().stream().filter(c -> command.isInstance(c)).collect(Collectors.toList()).forEach(c -> {
			this.handle.getEventManager().removeMessageListener(c);
			this.getCommands().remove(c);
		});
	}

}
