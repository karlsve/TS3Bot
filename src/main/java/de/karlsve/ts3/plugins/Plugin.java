package de.karlsve.ts3.plugins;

import de.karlsve.ts3.ServerBot;

public interface Plugin {
	
	void onLoad(ServerBot handle);
	void onUnload(ServerBot handle);
	
}
