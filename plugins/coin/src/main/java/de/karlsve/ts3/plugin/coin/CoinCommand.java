package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.events.PrivateMessageEvent;
import de.karlsve.ts3.command.PrivateCommand;

public class CoinCommand extends PrivateCommand {

    public CoinCommand() {
        super("\\!coin");
    }

    @Override
    public void onCommand(PrivateMessageEvent evt) {
        try {
            evt.client.sendPrivateMessage(evt.invokerId, "You got " + (Math.random() < 0.5 ? "heads" : "tails"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}