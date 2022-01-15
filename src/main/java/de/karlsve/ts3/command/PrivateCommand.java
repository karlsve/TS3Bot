package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;

import de.karlsve.ts3.ServerBot;

public abstract class PrivateCommand extends Command {

	public PrivateCommand(ServerBot handle, String pattern) {
		super(handle, pattern);
	}

	@Override
	public TextMessageTargetMode getTargetMode() {
		return TextMessageTargetMode.CLIENT;
	}
	
}
