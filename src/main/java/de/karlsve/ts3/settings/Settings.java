package de.karlsve.ts3.settings;

import java.util.HashMap;

public class Settings extends HashMap<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9145885189409095124L;

	public String get(String key, String defaultValue) {
		return this.getOrDefault(key, defaultValue);
	}
	
}
