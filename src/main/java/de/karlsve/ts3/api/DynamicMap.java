package de.karlsve.ts3.api;

import java.util.HashMap;

public class DynamicMap<T> extends HashMap<T, Object> {

    public <K> K getTyped(T key) {
        return (K) this.get(key);
    }

}