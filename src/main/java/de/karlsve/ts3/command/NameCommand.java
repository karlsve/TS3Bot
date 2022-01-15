package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.ServerBot;

public class NameCommand extends GlobalCommand {
	
	public NameCommand(ServerBot handle) {
		super(handle, "!"+handle.getClient().getNickname());
	}

	@Override
	public void onTrigger(TextMessageEvent event) {
		try {
			this.getHandle().getClient().sendPrivateMessage(event.getInvokerId(), "How can I help you?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
