package de.karlsve.ts3.command;

import java.util.regex.Pattern;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.events.MessageListener;

public interface ICommand extends MessageListener {
	
	public void onTrigger(TextMessageEvent evt);
	
	public TextMessageTargetMode getTargetMode();
	
	public Pattern getPattern();
	
	public ServerBot getHandle();
	
}
