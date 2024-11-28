package io.github.fruitcropxl.config.util.exceptions;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String name) {
        super("Could not find: '" + name
                + "'. Check if the correct type is given.");
    }

    public KeyNotFoundException(String name, String message) {
        super("'" + name + "', " + message);
    }
}
