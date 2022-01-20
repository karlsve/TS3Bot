package de.karlsve.ts3.events;

import com.github.manevolent.ts3j.event.ClientJoinEvent;
import com.github.manevolent.ts3j.event.ClientLeaveEvent;
import com.github.manevolent.ts3j.event.ClientMovedEvent;

import de.karlsve.ts3.ServerBot;

public interface ClientListener {

	void onJoin(ServerBot handle, ClientJoinEvent clientEvent);
	void onLeave(ServerBot handle, ClientLeaveEvent clientEvent);
	void onMoved(ServerBot handle, ClientMovedEvent clientEvent);

}
