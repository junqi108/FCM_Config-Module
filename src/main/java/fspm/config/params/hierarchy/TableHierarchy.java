package fspm.config.params.hierarchy;

import java.io.FileNotFoundException;

import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;

public class TableHierarchy extends Hierarchy {

    public TableHierarchy(String groupKey) {
        super(groupKey);
    }
    
    public static TableHierarchy parse(String path) throws FileNotFoundException {
        JsonNode tree = JsonFileReader.getTreeFromFile(path);

        TableHierarchy hierarchy = new TableHierarchy(path);

        return hierarchy;
    }
}
