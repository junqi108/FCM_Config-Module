package fspm.config.adapters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fspm.config.adapters.handlers.DictionaryHandler;
import fspm.config.adapters.handlers.DocumentCategoryNameHandler;
import fspm.config.adapters.handlers.DocumentHybridCategoryNameHandler;
import fspm.config.params.group.ParamGroup;

/**
 * File reader to parse JSON config files to {@link Config}.
 * 
 * @author Ou-An Chuang
 */
public class JsonFileReader extends ConfigAdapter {

    public JsonFileReader(String path) {
        super(path);
    }

    @Override
    public ParamGroup parse() throws FileNotFoundException {
        // Get node structure from JSON file
        JsonNode tree = getTreeFromFile(path);
        String metaclass = tree.get("metaclass").asText();

        switch (metaclass) {
            case "document-category-name":
                return new DocumentCategoryNameHandler().parse(path);
            case "document-hybrid-category-name":
                return new DocumentHybridCategoryNameHandler().parse(path);
            case "dictionary":
                return new DictionaryHandler().parse(path);
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
    public static JsonNode getTreeFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(file);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

}
