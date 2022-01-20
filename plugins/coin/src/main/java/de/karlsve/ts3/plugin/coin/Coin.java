package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.plugins.Plugin;
import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.command.CommandManager;
import de.karlsve.ts3.plugin.coin.CoinCommand;

/**
 * Hello world!
 *
 */
public class Coin implements Plugin {

    private final CoinCommand privateCommand;
    private final ServerCoinCommand serverCommand;

    public Coin() {
        this.privateCommand = new CoinCommand();
        this.serverCommand = new ServerCoinCommand();
    }

    @Override
    public void onLoad() {
        CommandManager.getInstance().addCommand(this.privateCommand);
        CommandManager.getInstance().addCommand(this.serverCommand);
    }

    @Override
    public void onUnload() {
        CommandManager.getInstance().removeCommand(this.privateCommand);
        CommandManager.getInstance().removeCommand(this.serverCommand);
    }

}
