package de.karlsve.ts3.command;

import de.karlsve.ts3.events.Event;
import de.karlsve.ts3.events.ChannelMessageEvent;
import de.karlsve.ts3.events.ChannelMessageListener;

public abstract class ChannelCommand extends ChannelMessageListener implements Command<ChannelMessageEvent> {

    @Override
	public boolean matches(Event event) {
		return super.matches(event) && ((ChannelMessageEvent) event).message.matches(this.getPattern());
	}
}
