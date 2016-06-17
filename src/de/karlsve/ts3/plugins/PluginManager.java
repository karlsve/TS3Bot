package de.karlsve.ts3.plugins;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import de.karlsve.ts3.Log;
import de.karlsve.ts3.ServerBot;

public class PluginManager {

	class JARFileFilter implements FileFilter {

		public boolean accept(File f) {
			return f.getName().toLowerCase().endsWith(".jar");
		}

	}

	private static Object lock = new Object();
	public static final String DEFAULT_DIRECTORY = "plugins";

	private ServerBot handle = null;
	private Vector<Plugin> plugins = new Vector<>();
	private String directory = PluginManager.DEFAULT_DIRECTORY;

	public PluginManager(ServerBot handle) {
		this.handle = handle;
		Log.d("PluginManager starting...");
		synchronized (PluginManager.lock) {
			try {
				this.plugins.addAll(this.loadPlugins());
			} catch (IOException e) {
				Log.e(e);
			}
		}
	}
	
	public Plugin getPlugin(Class<? extends Plugin> clazz) {
		synchronized(PluginManager.lock) {
			for(Plugin plugin : this.plugins) {
				if(clazz.isInstance(plugin)) {
					return plugin;
				}
			}
		}
		return null;
	}

	public void load() {
		synchronized (PluginManager.lock) {
			Log.d("Loading plugins...");
			for (Plugin plugin : this.plugins) {
				plugin.onLoad(this.handle);
				Log.d("Plugin loaded: " + plugin.toString());
			}
		}
	}

	public void unload() {
		synchronized (PluginManager.lock) {
			Log.d("Loading unloading...");
			for (Plugin plugin : this.plugins) {
				plugin.onUnload(this.handle);
				Log.d("Plugin unloaded: " + plugin.toString());
			}
		}
	}

	public void reload() throws IOException {
		synchronized (PluginManager.lock) {
			this.plugins.clear();
			this.plugins.addAll(this.loadPlugins());
		}
	}

	private String loadPluginDirectory() {
		if (this.handle.getSettings().containsKey("plugin_directory")) {
			return this.handle.getSettings().get("plugin_directory");
		}
		return PluginManager.DEFAULT_DIRECTORY;
	}

	private void initPluginDirectory() {
		File dir = new File(this.directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	private List<Plugin> loadPlugins() throws IOException {
		this.directory = this.loadPluginDirectory();
		this.initPluginDirectory();
		this.plugins.clear();
		File[] files = new File(this.directory).listFiles(new JARFileFilter());
		URL[] urls = PluginManager.fileArrayToURLArray(files);
		URLClassLoader loader = new URLClassLoader(urls);
		List<Class<Plugin>> pluginClasses = PluginManager.extractClassesFromJARs(files, loader);
		return PluginManager.instantiatePlugins(pluginClasses);
	}

	private static URL[] fileArrayToURLArray(File[] files) throws MalformedURLException {

		URL[] urls = new URL[files.length];
		for (int i = 0; i < files.length; i++) {
			Log.d("Adding plugin file: "+files[i].getName());
			urls[i] = files[i].toURI().toURL();
		}
		return urls;
	}

	private static List<Class<Plugin>> extractClassesFromJARs(File[] jars, ClassLoader cl) throws IOException {
		List<Class<Plugin>> classes = new ArrayList<Class<Plugin>>();
		for (File jar : jars) {
			classes.addAll(PluginManager.extractClassesFromJAR(jar, cl));
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	private static List<Class<Plugin>> extractClassesFromJAR(File jar, ClassLoader cl) throws IOException {
		List<Class<Plugin>> classes = new ArrayList<Class<Plugin>>();
		JarInputStream jaris = new JarInputStream(new FileInputStream(jar));
		JarEntry ent = null;
		while ((ent = jaris.getNextJarEntry()) != null) {
			if (ent.getName().toLowerCase().endsWith(".class")) {
				try {
					Class<?> cls = cl
							.loadClass(ent.getName().substring(0, ent.getName().length() - 6).replace('/', '.'));
					if (PluginManager.isPlugin(cls)) {
						classes.add((Class<Plugin>) cls);
					}
				} catch (ClassNotFoundException e) {
					Log.e("Can't load Class " + ent.getName());
					Log.d(e);
				}
			}
		}
		jaris.close();
		return classes;
	}

	private static boolean isPlugin(Class<?> cls) {
		for (Class<?> i : cls.getInterfaces()) {
			if (i.equals(Plugin.class)) {
				return true;
			}
		}
		return false;
	}

	private static List<Plugin> instantiatePlugins(List<Class<Plugin>> plugins) {
		List<Plugin> plugs = new ArrayList<Plugin>(plugins.size());
		for (Class<Plugin> plug : plugins) {
			try {
				plugs.add(plug.newInstance());
			} catch (InstantiationException e) {
				Log.e("Can't instantiate plugin: " + plug.getName());
				Log.e(e);
			} catch (IllegalAccessException e) {
				Log.e("IllegalAccess for plugin: " + plug.getName());
				Log.e(e);
			}
		}
		return plugs;
	}

}
