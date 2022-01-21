package de.karlsve.ts3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import de.karlsve.ts3.client.Client;
import de.karlsve.ts3.client.IdentityClient;
import de.karlsve.ts3.client.ServerQueryClient;
import de.karlsve.ts3.command.CommandManager;
import de.karlsve.ts3.events.Event;
import de.karlsve.ts3.events.EventManager;
import de.karlsve.ts3.plugins.PluginManager;
import de.karlsve.ts3.settings.ArgumentSettingsFactory;
import de.karlsve.ts3.settings.FileSettingsFactory;
import de.karlsve.ts3.settings.Settings;

public class ServerBot {

    class TickEvent extends Event {

        public final long time;

        public TickEvent(long time) {
            super("tick");
            this.time = time;
        }

        @Override
        public String toString() {
            return super.toString() + ": " + this.time;
        }

    }
    
    private static ServerBot instance;
    public static ServerBot getInstance() {
        if(instance == null) {
            throw new IllegalStateException("ServerBot not initialized");
        }
        return ServerBot.instance;
    }

    private Settings settings = null;
    private Client client = null;
    
    private final Runnable ticker = new Runnable() {
        @Override
        public void run() {
            EventManager.getInstance().trigger(new TickEvent(System.currentTimeMillis()));
        }
    };
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ServerBot(String[] args) {
        ServerBot.instance = this;
        this.settings = ArgumentSettingsFactory.readSettings(args);
        if (this.settings.containsKey("config")) {
            String filename = this.settings.get("config");
            Log.d(filename);
            this.settings = FileSettingsFactory.readFileSettings(filename);
        }
    }

    private void init() {
        Log.d("ServerBot starting...");
        try {
            PluginManager.getInstance().load();
            this.client = new ServerQueryClient();
            this.client.connect();
            Log.d("ServerBot running...");
        } catch (Exception e) {
            Log.e(new Exception("ServerBot error during start..."));
            Log.e(e);
            this.shutdown();
        }
    }

    public void run() {
        Log.d("ServerBot starting...");
        try {
            CommandManager.getInstance();
            PluginManager.getInstance().load();
            boolean useQuery = Settings.getInstance().get("query", true);
            this.client = useQuery ? new ServerQueryClient() : new IdentityClient();
            this.client.connect();
            this.scheduler.scheduleAtFixedRate(this.ticker, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
            EventManager.getInstance().trigger(new Event("started"));
            Log.d("ServerBot running...");
            while(!this.scheduler.isShutdown()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Log.e(e);
                    break;
                }
            }
            EventManager.getInstance().trigger(new Event("stopped"));
        } catch (Exception e) {
            Log.e(e);
        } finally {
            this.disconnect();
            PluginManager.getInstance().unload();
        }
    }

    public static void main(String[] args) {
        ServerBot bot = new ServerBot(args);
        bot.run();
    }

    public void restart() throws Exception {
        Log.d("ServerBot restarting...");
        this.disconnect();
        this.init();
    }

    private void disconnect() {
        EventManager.getInstance().trigger(new Event("disconnecting"));
        if (this.client != null) {
            try {
                this.client.disconnect();
            } catch (Exception e) {
                Log.e(e);
            }
        }
        EventManager.getInstance().trigger(new Event("disconnected"));
    }

    public void shutdown() {
        Log.d("ServerBot stopping...");
        this.scheduler.shutdownNow();
    }

}
