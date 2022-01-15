package de.karlsve.ts3.events;

import com.github.manevolent.ts3j.api.TextMessageTargetMode;
import com.github.manevolent.ts3j.event.TextMessageEvent;

public interface MessageListener {
	TextMessageTargetMode getTargetMode();
	void onMessageReceived(TextMessageEvent event);
	void onMessageSent(TextMessageEvent event);
}
