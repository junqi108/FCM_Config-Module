package io.github.fruitcropxl.config.util;

import java.io.Serializable;

/**
 * Represents an element which contains a key.
 */
public abstract class KeyElement implements Serializable {
    private String key;

    public KeyElement(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
