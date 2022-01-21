package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.command.ServerCommand;
import de.karlsve.ts3.events.ServerMessageEvent;

public class ServerCoinCommand extends ServerCommand {

    @Override
    public String getPattern() {
        return "^\\!coin$";
    }

    @Override
    public void onCommand(ServerMessageEvent evt) {
        try {
            evt.client.sendServerMessage("You got " + (Math.random() < 0.5 ? "heads" : "tails"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}