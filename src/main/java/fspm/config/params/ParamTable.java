package fspm.config.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fspm.util.KeyElement;
import fspm.util.exceptions.KeyConflictException;

public class ParamTable extends KeyElement {
    /**
     * A map of tabular rows stored with <row name, list of parameters>.
     * For example, [layer_1, layerThickness].
     */
    private Map<String, List<Parameter>> rows;

    public ParamTable(String key) {
        super(key);
        rows = new HashMap<>();
    }

    public void add(String key, List<Parameter> row) {
        // Use parameter key as unique identifier
        if (rows.containsKey(key)) {
            throw new KeyConflictException(key);
        } else {
            set(key, row);
        }
    }

    private void set(String key, List<Parameter> row) {
        rows.put(key, row);
    }

    // public <T> T getValue(String row, String column) {
    // return 1;
    // }

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
