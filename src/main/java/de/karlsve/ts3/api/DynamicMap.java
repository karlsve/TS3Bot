package de.karlsve.ts3.api;

import java.util.HashMap;

public class DynamicMap<T> extends HashMap<T, Object> {

	public <K> K get(String key, K defaultValue) {
		return this.containsKey(key) ? this.get(key) : defaultValue;
	}

    @SuppressWarnings("unchecked")
	public <K> K get(String key) {
        return (K) super.get(key);
    }

}