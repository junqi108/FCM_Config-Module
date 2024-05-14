package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.group.ParamGroup;

public class DictionaryHandler extends MetaclassHandler {
	public ParamGroup parse(String path) throws FileNotFoundException {
		JsonNode tree = JsonFileReader.getTreeFromFile(path);
		
		// ParamGroup config = new ParamGroup(path);
		
		// TODO: special parsing for dictionary type
		
		return null;
	}
}
