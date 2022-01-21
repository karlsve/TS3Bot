package de.karlsve.ts3.command;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.api.DynamicMap;
import de.karlsve.ts3.events.PrivateMessageEvent;

public class JoinCommand extends PrivateCommand {

    public String getPattern() {
        return "\\!join";
    }

    @Override
    public void onCommand(PrivateMessageEvent event) {
        try {
            DynamicMap<String> user = event.client.clientInfo(event.invokerId);
            event.client.joinChannel((int) user.get("channel_id"), null);
        } catch (Exception e) {
            Log.e(e);
        }
    }
    
}
