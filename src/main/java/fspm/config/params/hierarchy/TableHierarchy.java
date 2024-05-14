package fspm.config.params.hierarchy;

import java.io.FileNotFoundException;

import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamFactory;
import fspm.config.params.ParamTable;

public class TableHierarchy extends Hierarchy {

    public TableHierarchy(String groupKey) {
        super(groupKey);
    }
    
    public static TableHierarchy parse(String path) throws FileNotFoundException {
        JsonNode tree = JsonFileReader.getTreeFromFile(path);

        TableHierarchy hierarchy = new TableHierarchy(path);

        ParamFactory paramFactory = new ParamFactory();

        JsonNode tables = tree.get("tables");

        return hierarchy;
    }

    // public ParamTable getTable(String key) {
        
    // }


    // Table soilPhysicalProperties = CONFIG.getTable("soilPhysicalProperties")

    // soilPhysicalProperties.getValue("layer_" + i, "layerThickness"); // indexing using row, col
}
