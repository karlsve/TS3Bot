package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.command.ServerCommand;
import de.karlsve.ts3.events.ServerMessageEvent;

public class ServerCoinCommand extends ServerCommand {

    public ServerCoinCommand() {
        super("\\!coin");
    }

    @Override
    public void onCommand(ServerMessageEvent evt) {
        try {
            handle.getClient().sendServerMessage("You got " + (Math.random() < 0.5 ? "heads" : "tails"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}