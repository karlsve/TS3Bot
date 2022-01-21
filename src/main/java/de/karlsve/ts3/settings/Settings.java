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
	
}
