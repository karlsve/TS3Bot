package de.karlsve.ts3.events;

public abstract class ChannelMessageListener extends MessageListener<ChannelMessageEvent> {

    public final int channelId;

    public ChannelMessageListener() {
        this(-1);
    }

    public ChannelMessageListener(int channelId) {
        this.channelId = channelId;
    }

    public boolean matches(Event event) {
        return event instanceof ChannelMessageEvent && this.channelId == -1 || ((ChannelMessageEvent) event).receiverId == this.channelId;
    }

}
