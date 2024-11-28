package io.github.fruitcropxl.config.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.fruitcropxl.config.util.KeyElement;
import io.github.fruitcropxl.config.util.exceptions.KeyConflictException;
import io.github.fruitcropxl.config.util.exceptions.KeyNotFoundException;

/**
 * Stores {@link io.github.fruitcropxl.config.params.Parameter Parameters} in a tabular format as
 * rows and columns (parameter key).
 */
public class ParamTable extends KeyElement {
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

    // public <T> T getValue(String row, String column) {
    // Parameter param = getParameter(row, column);

    // }

    // FIXME: duplicate code with ParamCategory
    // TODO: consider setter for table

    public Boolean getBoolean(String row, String column) {
        return getParameter(row, column).asBoolean();
    }

    public String getString(String row, String column) {
        return getParameter(row, column).asString();
    }

    public Integer getInteger(String row, String column) {
        return getParameter(row, column).asInteger();
    }

    public Double getDouble(String row, String column) {
        return getParameter(row, column).asDouble();
    }

    public <T> T[] getArray(String row, String column, Class<T[]> type) {
        return getParameter(row, column).asArray(type);
    }

    public boolean isNull(String row, String column) {
        return getParameter(row, column).isNull();
    }

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
