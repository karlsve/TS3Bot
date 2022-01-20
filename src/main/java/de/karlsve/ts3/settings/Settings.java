package de.karlsve.ts3.settings;

import de.karlsve.ts3.api.DynamicMap;

public class Settings extends DynamicMap<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9145885189409095124L;

	private static Settings instance;
	public static Settings getInstance() {
		if(instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	private Settings() {
		super();
	}

	public <T> T get(String key, T defaultValue) {
		return (T) this.getOrDefault(key, defaultValue);
	}

	public <T> T get(String key) {
		return (T) super.get(key);
	}
	
}
