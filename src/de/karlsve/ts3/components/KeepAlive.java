package de.karlsve.ts3.components;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.ServerBot.TickListener;

public class KeepAlive implements TickListener {

	private static final String COMMAND = "clientupdate";

	public KeepAlive(ServerBot handle) {
		Log.d("KeepAlive running...");
		handle.addListener(this);
	}

	@Override
	public void onTick(ServerBot handle) {
		if (!handle.getQuery().isConnected()) {
			this.reconnect(handle);
		} else if(!handle.getQuery().doCommand(KeepAlive.COMMAND).get("msg").equalsIgnoreCase("ok")) {
			this.reconnect(handle);
		}
	}

	private void reconnect(ServerBot handle) {
		try {
			handle.restart();
		} catch (Exception e) {
			Log.e(e);
		}
	}

}
