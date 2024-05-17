package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;

import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;

public class DocumentCategoryNameHandler extends MetaclassHandler {
	public DocumentCategoryNameGroup parse(String path) throws FileNotFoundException {
		CategoryStore store = CategoryStore.parse(path);

		DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(path, store);
		return group;
	}
}
