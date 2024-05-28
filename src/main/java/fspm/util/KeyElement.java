package fspm.util;

/**
 * Represents an element which contains a key.
 */
public abstract class KeyElement {
    private String key;

    public KeyElement(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
