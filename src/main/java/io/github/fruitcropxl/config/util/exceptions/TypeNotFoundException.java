package io.github.fruitcropxl.config.util.exceptions;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(String name, String type) {
        super(getFormattedString(name, type));
    }

    public TypeNotFoundException(String name, String type, String message) {
        super(getFormattedString(name, type) + " " + message);
    }

    private static String getFormattedString(String name, String type) {
        return String.format("Could not find key: '%s' of type: %s.", name,
                type);
    }
}
