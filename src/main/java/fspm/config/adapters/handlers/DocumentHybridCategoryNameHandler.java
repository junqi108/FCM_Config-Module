package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.group.DocumentHybridCategoryNameGroup;

public class DocumentHybridCategoryNameHandler extends MetaclassHandler {
	public DocumentHybridCategoryNameGroup parse(String path) throws FileNotFoundException {
		JsonNode tree = JsonFileReader.getTreeFromFile(path);
		
		DocumentHybridCategoryNameGroup config = new DocumentHybridCategoryNameGroup(path);
		
		// TODO: special parsing for dictionary type
		
		return config;
	}
}
