package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;

public class DocumentCategoryNameHandler extends MetaclassHandler {
	public DocumentCategoryNameGroup parse(String path) throws FileNotFoundException {
		CategoryHierarchy hierarchy = CategoryHierarchy.parse(path);

		DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(path, hierarchy);
		return group;
	}
}
