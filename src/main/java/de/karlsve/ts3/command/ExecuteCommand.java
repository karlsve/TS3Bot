package de.karlsve.ts3.command;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.events.PrivateMessageEvent;

public class ExecuteCommand extends PrivateCommand {

	@Override
	public String getPattern() {
		return "^!execute .*?$";
	}

	@Override
	public void onCommand(PrivateMessageEvent event) {
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
