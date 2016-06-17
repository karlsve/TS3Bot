package de.karlsve.ts3.events;

import java.util.HashMap;

import de.karlsve.ts3.ServerBot;

public class MessageEvent extends Event {

	private static final String TARGET_MODE = "targetmode";
	public static final int TARGET_MODE_SERVER = 3;
	public static final int TARGET_MODE_CHANNEL = 2;
	public static final int TARGET_MODE_PRIVATE = 1;

	private static final String INVOKER_ID = "invokerid";
	private static final String INVOKER_NAME = "invokername";
	private static final String INVOKER_UID = "invokername";
	private static final String TARGET = "target";
	private static final String MESSAGE = "msg";

	private int targetmode = 0;
	private int invoker_id = 0;
	private String invoker_name = "";
	private String invoker_uid = "";
	private int target = -1;
	private String message = "";

	public MessageEvent(ServerBot handle, String type, HashMap<String, String> eventInfo) {
		super(handle, type, eventInfo);
		this.targetmode = Integer.valueOf(eventInfo.get(TARGET_MODE));
		this.invoker_id = Integer.valueOf(eventInfo.get(INVOKER_ID));
		this.invoker_name = eventInfo.get(INVOKER_NAME);
		this.invoker_uid = eventInfo.get(INVOKER_UID);
		if (this.targetmode != TARGET_MODE_SERVER) {
			this.target = Integer.valueOf(eventInfo.get(TARGET));
		}
		this.message = eventInfo.get(MESSAGE);
	}

	public String getInvokerName() {
		return this.invoker_name;
	}

	public int getInvokerId() {
		return this.invoker_id;
	}

	public String getInvokerUID() {
		return this.invoker_uid;
	}

	public int getTarget() {
		return this.target;
	}

	public int getTargetMode() {
		return this.targetmode;
	}

	public String getMessage() {
		return this.message;
	}

	public boolean isReceived() {
		return MessageEvent.this.getInvokerId() != this.getHandle().getQuery().getCurrentQueryClientID();
	}

}
