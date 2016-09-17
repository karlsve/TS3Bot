package de.karlsve.ts3.settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Scanner;

import de.karlsve.ts3.Log;

public abstract class FileSettingsFactory {

	public static final String DEFAULT_FILENAME = "config.ini";

	public static Settings readFileSettings(String filename) {
		File file = new File(filename);
		Scanner scanner = null;
		Settings settings = new Settings();
		if (file.exists()) {
			Log.d("Settings file found.");
			try {
				scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.contains("=") && !line.startsWith("#")) {
						String[] pair = line.split("=");
						if (pair.length == 1) {
							settings.put(pair[0], "");
						} else {
							settings.put(pair[0], pair[1]);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (scanner != null) {
					scanner.close();
				}
			}
		}
		return settings;
	}
	
	public static void writeFileSettings(String filename, Settings settings) {
		File file = new File(filename);
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			writer.write("");
			for(Map.Entry<String, String> entry : settings.entrySet()) {
				String line = entry.getKey() + "=" + entry.getValue();
				writer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
