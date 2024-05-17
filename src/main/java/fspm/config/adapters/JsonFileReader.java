package fspm.config.adapters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.groups.DocumentHybridCategoryNameGroup;
import fspm.config.params.groups.ParamGroup;

/**
 * File reader to parse JSON config files to {@link Config}.
 * 
 * @author Ou-An Chuang
 */
public class JsonFileReader extends ConfigAdapter {

    public static final String METACLASS_HEADER = "metaclass";

    public JsonFileReader(String path) {
        super(path);
    }

    @Override
    public ParamGroup parse() throws FileNotFoundException {
        // Get node structure from JSON file
        JsonNode tree = getTreeFromFile(path);
        String metaclass = tree.get(METACLASS_HEADER).asText();

        switch (metaclass) {
        case "document-category-name":
            return DocumentCategoryNameGroup.parse(path);
        case "document-hybrid-category-name":
            return DocumentHybridCategoryNameGroup.parse(path);
        }
        throw new UnsupportedOperationException(metaclass);
    }

    /**
     * Helper function for getting root/tree node from a JSON file.
     * 
     * @param filePath Path to JSON file.
     * @return Root/tree node.
     * @throws FileNotFoundException
     */
    public static JsonNode getTreeFromFile(String filePath)
            throws FileNotFoundException {
        File file = new File(filePath);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(file);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

}
