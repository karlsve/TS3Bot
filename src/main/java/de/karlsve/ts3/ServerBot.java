package de.karlsve.ts3;

import java.util.Vector;

import de.karlsve.ts3.command.CommandManager;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.plugins.PluginManager;
import de.karlsve.ts3.settings.ArgumentSettingsFactory;
import de.karlsve.ts3.settings.FileSettingsFactory;
import de.karlsve.ts3.settings.Settings;
import de.stefan1200.jts3serverquery.JTS3ServerQuery;

public class ServerBot implements Runnable {

    private Settings settings = null;
    private JTS3ServerQuery query = null;
    private EventManager eventManager;
    private CommandManager commandManager;

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
            this.settings = FileSettingsFactory.readFileSettings(filename);
        }
    }

    public void init() {
        Log.d("ServerBot starting...");
        this.query = new JTS3ServerQuery();
        this.eventManager = new EventManager(this);
        try {
            this.connect();
            this.login();
            this.selectServer();
            this.query.setDisplayName(this.settings.get("name"));
            this.startComponents();
            Log.d("ServerBot running...");
        } catch (Exception e) {
            Log.e(new Exception("ServerBot error during start..."));
            Log.e(e);
        }
    }

    synchronized private void connect() throws Exception {
        if (this.settings.containsKey("ip") && this.settings.containsKey("port")) {
            Log.d("ServerBot connecting...");
            this.query.connectTS3Query(this.settings.get("ip"), Integer.valueOf(this.settings.get("port")));
            return;
        }
        throw new Exception("IP and/or Port missing...");
    }

    synchronized public void login() throws Exception {
        if (this.settings.containsKey("username") && this.settings.containsKey("password")) {
            Log.d("ServerBot logging in...");
            this.query.loginTS3(this.settings.get("username"), this.settings.get("password"));
            return;
        }
        throw new Exception("Username and/or password missing...");
    }

    private void selectServer() throws Exception {
        if (this.settings.containsKey("sid")) {
            Log.d("ServerBot selecting server...");
            this.query.selectVirtualServer(Integer.valueOf(this.settings.get("sid")));
            return;
        }
        throw new Exception("Server ID missing...");
    }

    private void startComponents() {
        this.eventManager.init();
        this.commandManager = new CommandManager(this);
        this.pluginManager = new PluginManager(this);
        this.pluginManager.load();
    }

    public Settings getSettings() {
        return this.settings;
    }

    public JTS3ServerQuery getQuery() {
        return this.query;
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
        this.pluginManager.unload();
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
        if (this.getQuery() != null) {
            if (this.getQuery().isConnected()) {
                this.getQuery().closeTS3Connection();
            }
        }
    }

    public void shutdown() {
        Log.d("ServerBot stopping...");
        this.run = false;
    }

}
