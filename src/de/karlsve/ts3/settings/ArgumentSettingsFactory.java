package de.karlsve.ts3.settings;

public abstract class ArgumentSettingsFactory {

	public static Settings readSettings(String[] args) {
		Settings settings = new Settings();
		for (String arg : args) {
			if (arg.contains("=") && !arg.startsWith("#")) {
				String[] split = arg.split("=");
				if (split.length == 2) {
					settings.put(split[0], split[1]);
				} else if(split.length == 1) {
					settings.put(split[0], "");
				}
			}
		}
		return settings;
	}

}
