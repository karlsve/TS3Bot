package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.command.PrivateCommand;
import com.github.manevolent.ts3j.event.TextMessageEvent;
import de.karlsve.ts3.ServerBot;

public class CoinCommand extends PrivateCommand {

    public CoinCommand(ServerBot handle) {
        super(handle, "\\!coin");
    }

    @Override
    public void onTrigger(TextMessageEvent evt) {
        try {
            this.getHandle().getClient().sendPrivateMessage(evt.getInvokerId(), "You got " + (Math.random() < 0.5 ? "heads" : "tails"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}