package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamFactory;
import fspm.config.params.Parameter;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.group.ParamGroup;

public class DocumentCategoryNameHandler extends MetaclassHandler {
	public DocumentCategoryNameGroup parse(String path) throws FileNotFoundException {
		JsonNode tree = JsonFileReader.getTreeFromFile(path);
		
		DocumentCategoryNameGroup config = new DocumentCategoryNameGroup(path);
		ParamFactory paramFactory = new ParamFactory();
        
        JsonNode categoriesNode = tree.get("category");
        Iterator categoryNames = categoriesNode.fieldNames();

        // Parse each category node
        for (JsonNode categoryNode : categoriesNode) {
            String categoryName = categoryNames.next().toString();
            ParamCategory category = new ParamCategory(categoryName);

            Iterator paramNames = categoryNode.fieldNames();

            // Parse each parameter node
            for (JsonNode paramNode : categoryNode) {
                Parameter param = paramFactory.getParam(paramNames.next().toString(), paramNode);

                // null if paramNode type is unsupported
                // TODO: use checked exception for UnsupportedException
                if (param != null) {
                    category.add(param);
                }
            }
            config.addCategory(category);
        }	
		return config;
	}
}
