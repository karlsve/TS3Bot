package de.karlsve.ts3.command;

import java.util.ArrayList;
import java.util.List;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;

public class CommandManager {
	
	private ServerBot handle = null;
	private List<Command> commands = new ArrayList<>();

	public CommandManager(ServerBot handle) {
		this.handle = handle;
		this.init();
	}
	
	public List<Command> getCommands() {
		return this.commands;
	}

	private void init() {
		Log.d("CommandService starting...");
		this.initCommands();
		Log.d("CommandService running...");
	}

	private void initCommands() {
		this.registerCommand(new NameCommand(this.handle));
		this.registerCommand(new HelpCommand(this.handle));
		//this.registerCommand(new ExecuteCommand(this.handle));
		//this.registerCommand(new ShutdownCommand(this.handle));
	}

	public void registerCommand(Command command) {
		this.handle.getEventManager().addMessageListener(command);
		this.getCommands().add(command);
	}

	public void unregisterCommand(Command command) {
		this.handle.getEventManager().removeMessageListener(command);
		this.getCommands().remove(command);
	}

}
