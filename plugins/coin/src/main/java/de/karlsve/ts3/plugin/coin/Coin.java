package de.karlsve.ts3.plugin.coin;

import de.karlsve.ts3.plugins.Plugin;
import de.karlsve.ts3.ServerBot;
import de.karlsve.ts3.plugin.coin.CoinCommand;

/**
 * Hello world!
 *
 */
public class Coin implements Plugin {

    @Override
    public void onLoad(ServerBot handle) {
        handle.getCommandManager().registerCommand(CoinCommand.class);
    }

    @Override
    public void onUnload(ServerBot handle) {
        handle.getCommandManager().unregisterCommand(CoinCommand.class);
    }

}
