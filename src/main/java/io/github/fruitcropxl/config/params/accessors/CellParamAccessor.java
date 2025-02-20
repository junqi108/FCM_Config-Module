package io.github.fruitcropxl.config.params.accessors;

/**
 * Blueprint for all classes that handles getting and setting of parameters.
 */
public interface CellParamAccessor {
    <T> T get(String row, String column, Class<T> type);

    <T> T get(String row, String column, Class<T> type, T defaultValue);

    <T> T[] getArray(String row, String column, Class<T[]> type);

    <T> T[] getArray(String row, String column, Class<T[]> type,
            T[] defaultValue);

    boolean isNull(String row, String column);
}
