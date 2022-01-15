package de.karlsve.ts3;

import java.util.Vector;

import com.github.manevolent.ts3j.identity.Identity;
import com.github.manevolent.ts3j.identity.LocalIdentity;
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket;

import de.karlsve.ts3.command.CommandManager;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.plugins.PluginManager;
import de.karlsve.ts3.settings.ArgumentSettingsFactory;
import de.karlsve.ts3.settings.FileSettingsFactory;
import de.karlsve.ts3.settings.Settings;

public class ServerBot implements Runnable {

    private Settings settings = null;
    private EventManager eventManager;
    private Identity identity;
    private LocalTeamspeakClientSocket handle;
    private CommandManager commandManager;

    public LocalTeamspeakClientSocket getClient() {
        return this.handle;
    }

    private boolean run = true;

    public interface TickListener {
        void onTick(ServerBot handle);
    }

    private Vector<TickListener> listener = new Vector<>();
    private PluginManager pluginManager;

    public Vector<TickListener> getListener() {
        return this.listener;
    }

    public void addListener(TickListener listener) {
        this.getListener().addElement(listener);
    }

    public void removeListener(TickListener listener) {
        this.getListener().removeElement(listener);
    }

    public ServerBot(String[] args) {
        this.settings = ArgumentSettingsFactory.readSettings(args);
        if (this.settings.containsKey("config")) {
            String filename = this.settings.get("config");
            Log.d(filename);
            this.settings = FileSettingsFactory.readFileSettings(filename);
        }
    }

    public void init() {
        Log.d("ServerBot starting...");
        this.handle = new LocalTeamspeakClientSocket();
        this.eventManager = new EventManager(this.handle);
        try {
            this.identity = LocalIdentity.generateNew(10);
            this.handle.setIdentity(identity);
            this.handle.addListener(this.eventManager);
            this.handle.setNickname(this.settings.get("name", "ServerBot"));
            this.connect();
            this.startComponents();
            Log.d("ServerBot running...");
        } catch (Exception e) {
            Log.e(new Exception("ServerBot error during start..."));
            Log.e(e);
            this.shutdown();
        }
    }

    synchronized private void connect() throws Exception {
        if (this.settings.containsKey("addr")) {
            Log.d("ServerBot connecting...");
            this.handle.connect(
                this.settings.get("addr"),
                this.settings.get("password", null),
                Long.valueOf(this.settings.get("timeout", "1000"))
            );
            return;
        }
        throw new Exception("IP and/or Port missing...");
    }

    private void startComponents() {
        this.commandManager = new CommandManager(this);
        this.pluginManager = new PluginManager(this);
        this.pluginManager.load();
    }

    public Settings getSettings() {
        return this.settings;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public void run() {
        this.init();
        while (this.run) {
            new Thread(ServerBot.this::tick).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(e);
            }
        }
        if(this.pluginManager != null) {
            this.pluginManager.unload();
        }
        this.disconnect();
    }

    private void tick() {
        Vector<TickListener> listenerCopy = new Vector<>(this.listener);
        for (TickListener listener : listenerCopy) {
            listener.onTick(ServerBot.this);
        }
    }

    public static void main(String[] args) {
        new Thread(new ServerBot(args)).start();
    }

    public void restart() throws Exception {
        Log.d("ServerBot restarting...");
        this.disconnect();
        this.listener.removeAllElements();
        this.init();
    }

    private void disconnect() {
        Log.d("ServerBot disconnecting...");
        if (this.handle != null) {
            try {
                if (this.handle.isConnected()) {
                    this.handle.disconnect();
                }
                this.handle.close();
            } catch (Exception e) {
                Log.e(e);
            }
        }
    }

    public void shutdown() {
        Log.d("ServerBot stopping...");
        this.run = false;
    }

}
