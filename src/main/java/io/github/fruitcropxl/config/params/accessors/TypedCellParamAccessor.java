package io.github.fruitcropxl.config.params.accessors;

/**
 * These interface methods are necessary to implement a wrapper for generic parameter getters, as
 * some older versions of Java do not support generic return types.
 * 
 * For example, get("parameter", Boolean.class); will be treated as a java.lang.Object return type,
 * rather than the correct Boolean type.
 */
public interface TypedCellParamAccessor {

    Boolean getBoolean(String row, String column);

    Boolean getBoolean(String row, String column, Boolean defaultValue);

    String getString(String row, String column);

    String getString(String row, String column, String defaultValue);

    Integer getInteger(String row, String column);

    Integer getInteger(String row, String column, Integer defaultValue);

    Double getDouble(String row, String column);

    Double getDouble(String row, String column, Double defaultValue);

    Boolean[] getBooleanArray(String row, String column);

    Boolean[] getBooleanArray(String row, String column,
            Boolean[] defaultValue);

    String[] getStringArray(String row, String column);

    String[] getStringArray(String row, String column, String[] defaultValue);

    Integer[] getIntegerArray(String row, String column);

    Integer[] getIntegerArray(String row, String column,
            Integer[] defaultValue);

    Double[] getDoubleArray(String row, String column);

    Double[] getDoubleArray(String row, String column, Double[] defaultValue);
}
