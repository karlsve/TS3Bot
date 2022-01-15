package de.karlsve.ts3.events;

import com.github.manevolent.ts3j.event.ClientJoinEvent;
import com.github.manevolent.ts3j.event.ClientLeaveEvent;
import com.github.manevolent.ts3j.event.ClientMovedEvent;

public interface ClientListener {

	void onJoin(ClientJoinEvent clientEvent);
	void onLeave(ClientLeaveEvent clientEvent);
	void onMoved(ClientMovedEvent clientEvent);

}
