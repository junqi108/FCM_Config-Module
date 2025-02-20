package io.github.fruitcropxl.config.params.accessors;

/**
 * These interface methods are necessary to implement a wrapper for generic parameter getters, as
 * some older versions of Java do not support generic return types.
 * 
 * For example, get("parameter", Boolean.class); will be treated as a java.lang.Object return type,
 * rather than the correct Boolean type.
 */
public interface TypedKeyParamAccessor {

    Boolean getBoolean(String key);

    Boolean getBoolean(String key, Boolean defaultValue);

    String getString(String key);

    String getString(String key, String defaultValue);

    Integer getInteger(String key);

    Integer getInteger(String key, Integer defaultValue);

    Double getDouble(String key);

    Double getDouble(String key, Double defaultValue);

    Boolean[] getBooleanArray(String key);

    Boolean[] getBooleanArray(String key, Boolean[] defaultValue);

    String[] getStringArray(String key);

    String[] getStringArray(String key, String[] defaultValue);

    Integer[] getIntegerArray(String key);

    Integer[] getIntegerArray(String key, Integer[] defaultValue);

    Double[] getDoubleArray(String key);

    Double[] getDoubleArray(String key, Double[] defaultValue);
}
