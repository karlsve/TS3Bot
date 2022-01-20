package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.plugins.Plugin;
import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.plugin.coin.CoinCommand;

/**
 * Hello world!
 *
 */
public class Coin implements Plugin {

    private final CoinCommand privateCommand;

    public Coin() {
        this.privateCommand = new CoinCommand();
    }

    @Override
    public void onLoad() {

        CommandManager.getInstance().addCommand(this.privateCommand);
    }

    @Override
    public void onUnload() {
        CommandManager.getInstance().removeCommand(this.privateCommand);
    }

}
