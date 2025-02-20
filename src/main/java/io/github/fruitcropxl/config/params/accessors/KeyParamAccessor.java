package io.github.fruitcropxl.config.params.accessors;

/**
 * Blueprint for all classes that handles getting and setting of parameters.
 */
public interface KeyParamAccessor {
    <T> T get(String key, Class<T> type);

    <T> T get(String key, Class<T> type, T defaultValue);

    <T> T[] getArray(String key, Class<T[]> type);

    <T> T[] getArray(String key, Class<T[]> type, T[] defaultValue);

    boolean isNull(String key);
}
