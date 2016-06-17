package de.karlsve.ts3.plugins;

import de.karlsve.ts3.ServerBot;

public interface Plugin {
	
	public void onLoad(ServerBot handle);
	public void onUnload(ServerBot handle);
	
}
