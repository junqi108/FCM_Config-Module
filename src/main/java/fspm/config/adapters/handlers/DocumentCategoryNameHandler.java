package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamFactory;
import fspm.config.params.Parameter;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;

public class DocumentCategoryNameHandler extends MetaclassHandler {
	public DocumentCategoryNameGroup parse(String path) throws FileNotFoundException {
        CategoryHierarchy hierarchy = CategoryHierarchy.parse(path);

        DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(path, hierarchy);
		return group;
	}
}
