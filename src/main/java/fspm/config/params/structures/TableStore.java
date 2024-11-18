package fspm.config.params.structures;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamFactory;
import fspm.config.params.ParamTable;
import fspm.config.params.Parameter;
import fspm.util.exceptions.KeyConflictException;

/**
 * Represents the collection of tables in a document.
 */
public class TableStore extends ParamStructure {

    public static final String ROW_PREFIX_FIELD = "_row_prefix";

    private Map<String, ParamTable> tables;

    public TableStore(String groupKey) {
        super(groupKey);
        tables = new HashMap<>();
    }

    public static TableStore parse(String path) throws FileNotFoundException {
        JsonNode tree = JsonFileReader.getTreeFromFile(path);

        TableStore store = new TableStore(path);

        ObjectMapper mapper = new ObjectMapper();
        ParamFactory paramFactory = new ParamFactory();

        JsonNode tablesNode = tree.get("tables");

        try {
            String[] tableKeys = mapper.treeToValue(tablesNode, String[].class);

            // Parse each table
            for (String tableKey : tableKeys) {
                JsonNode tableNode = tree.get(tableKey);
                ParamTable table = new ParamTable(tableKey);

                Iterator layerKeys = tableNode.fieldNames();
                String rowPrefix = tableNode.get(ROW_PREFIX_FIELD).asText();

                // Parse each layer
                for (JsonNode layerNode : tableNode) {
                    String layerKey = layerKeys.next().toString();

                    if (rowPrefix != null && !layerKey.contains(rowPrefix)) {
                        continue;
                    }

                    List<Parameter> row = new ArrayList<>();

                    Iterator paramKeys = layerNode.fieldNames();

                    // Parse each layer
                    for (JsonNode paramNode : layerNode) {
                        String paramKey = paramKeys.next().toString();

                        Parameter param = paramFactory.parseParameter(paramKey,
                                paramNode);

                        // null if paramNode type is unsupported
                        // TODO: use checked exception for UnsupportedOperationException
                        if (param != null) {
                            row.add(param);
                        }
                    }
                    table.addRow(layerKey, row);
                }
                store.addTable(table);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            throw new RuntimeException(String.format(
                    "An error occurred while parsing table hierarchies in: %s.\n%s",
                    path, sw));
        }
        return store;
    }

    public void addTable(ParamTable table) {
        // Use category key as unique identifier
        if (tables.containsKey(table.getKey())) {
            throw new KeyConflictException(table.getKey());
        } else {
            tables.put(table.getKey(), table);
        }
    }

    public ParamTable getTable(String key) {
        return tables.get(key);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (ParamTable table : tables.values()) {
            string.append(table);
        }
        return string.toString();
    }
}
