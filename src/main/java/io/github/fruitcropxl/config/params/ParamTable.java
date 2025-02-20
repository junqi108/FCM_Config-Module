package io.github.fruitcropxl.config.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.fruitcropxl.config.params.accessors.CellParamAccessor;
import io.github.fruitcropxl.config.params.accessors.TypedCellParamAccessor;
import io.github.fruitcropxl.config.util.KeyElement;
import io.github.fruitcropxl.config.util.exceptions.KeyConflictException;
import io.github.fruitcropxl.config.util.exceptions.KeyNotFoundException;

/**
 * Stores {@link io.github.fruitcropxl.config.params.Parameter Parameters} in a tabular format as
 * rows and columns (parameter key).
 */
public class ParamTable extends KeyElement
        implements CellParamAccessor, TypedCellParamAccessor {
    /**
     * A map of tabular rows stored with (row name, list of parameters). For example, [layer_1,
     * layerThickness].
     */
    private Map<String, List<Parameter>> rows;

    public ParamTable(String key) {
        super(key);
        rows = new HashMap<>();
    }

    /**
     * Add a row of parameters to the table.
     * 
     * @param key Row key
     * @param row Row containing parameters.
     */
    public void addRow(String key, List<Parameter> row) {
        // Use parameter key as unique identifier
        if (rows.containsKey(key)) {
            throw new KeyConflictException(key);
        } else {
            setRow(key, row);
        }
    }

    private void setRow(String key, List<Parameter> row) {
        rows.put(key, row);
    }

    // TODO: consider setter for table

    private Parameter getParameter(String row, String column) {
        List<Parameter> paramRow = rows.get(row);

        for (Parameter param : paramRow) {
            if (param.getKey().equals(column)) {
                return param;
            }
        }
        // Did not find parameter.
        throw new KeyNotFoundException(column, String.format(
                "Could not find parameter '%s' in row '%s'", column, row));
    }

    @Override
    public <T> T get(String row, String column, Class<T> type) {
        return getParameter(row, column).getValue(type);
    }

    @Override
    public <T> T get(String row, String column, Class<T> type, T defaultValue) {
        T value = get(row, column, type);
        return value == null ? defaultValue : value;
    }

    @Override
    public <T> T[] getArray(String row, String column, Class<T[]> type) {
        return getParameter(row, column).asArray(type);
    }

    @Override
    public <T> T[] getArray(String row, String column, Class<T[]> type,
            T[] defaultValue) {
        T[] value = getParameter(row, column).asArray(type);
        return value == null ? defaultValue : value; // Check for null this way rather than isNull to avoid retrieving twice.
    }

    @Override
    public Boolean getBoolean(String row, String column) {
        return get(row, column, Boolean.class);
    }

    @Override
    public Boolean getBoolean(String row, String column, Boolean defaultValue) {
        return get(row, column, Boolean.class, defaultValue);
    }

    @Override
    public String getString(String row, String column) {
        return get(row, column, String.class);
    }

    @Override
    public String getString(String row, String column, String defaultValue) {
        return get(row, column, String.class, defaultValue);
    }

    @Override
    public Integer getInteger(String row, String column) {
        return get(row, column, Integer.class);
    }

    @Override
    public Integer getInteger(String row, String column, Integer defaultValue) {
        return get(row, column, Integer.class, defaultValue);
    }

    @Override
    public Double getDouble(String row, String column) {
        return get(row, column, Double.class);
    }

    @Override
    public Double getDouble(String row, String column, Double defaultValue) {
        return get(row, column, Double.class, defaultValue);
    }

    @Override
    public Boolean[] getBooleanArray(String row, String column) {
        return getArray(row, column, Boolean[].class);
    }

    @Override
    public Boolean[] getBooleanArray(String row, String column,
            Boolean[] defaultValue) {
        return getArray(row, column, Boolean[].class, defaultValue);
    }

    @Override
    public String[] getStringArray(String row, String column) {
        return getArray(row, column, String[].class);
    }

    @Override
    public String[] getStringArray(String row, String column,
            String[] defaultValue) {
        return getArray(row, column, String[].class, defaultValue);
    }

    @Override
    public Integer[] getIntegerArray(String row, String column) {
        return getArray(row, column, Integer[].class);
    }

    @Override
    public Integer[] getIntegerArray(String row, String column,
            Integer[] defaultValue) {
        return getArray(row, column, Integer[].class, defaultValue);
    }

    @Override
    public Double[] getDoubleArray(String row, String column) {
        return getArray(row, column, Double[].class);
    }

    @Override
    public Double[] getDoubleArray(String row, String column,
            Double[] defaultValue) {
        return getArray(row, column, Double[].class, defaultValue);
    }

    @Override
    public boolean isNull(String row, String column) {
        return getParameter(row, column).isNull();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Table: " + getKey() + "\n");

        for (Entry<String, List<Parameter>> row : rows.entrySet()) {
            string.append("- " + row.getKey() + " = ");
            for (Parameter param : row.getValue()) {
                string.append(String.format("[%s]", param));
            }
            string.append("\n");
        }

        return string.toString();
    }
}
