package de.karlsve.ts3.command;

import com.github.manevolent.ts3j.event.TextMessageEvent;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;

public class ExecuteCommand extends PrivateCommand {

	public ExecuteCommand(ServerBot handle) {
		super(handle, "^!execute .*?$");
	}

	@Override
	public void onTrigger(TextMessageEvent event) {
		Log.d("Not implemented yet");
		/*
		String query = event.getMessage().replaceFirst("^!execute ", "");
		Map<String, String> response = this.getHandle().getClient().executeCommand(new SingleCommand);
		try {
			event.getHandle().getQuery().sendTextMessage(event.getInvokerId(), MessageEvent.TARGET_MODE_PRIVATE,
					response.toString());
		} catch (TS3ServerQueryException e) {
			e.printStackTrace();
		}
		*/
	}

}
