package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;

import de.karlsve.ts3.ServerBot;

public abstract class GlobalCommand extends Command {

	public GlobalCommand(ServerBot handle, String pattern) {
		super(handle, pattern);
	}

	@Override
	public TextMessageTargetMode getTargetMode() {
		return TextMessageTargetMode.SERVER;
	}

}
