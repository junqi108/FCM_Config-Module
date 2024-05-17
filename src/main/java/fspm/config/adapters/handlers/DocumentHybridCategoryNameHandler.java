package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;

import fspm.config.params.groups.DocumentHybridCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;
import fspm.config.params.structures.TableStore;

public class DocumentHybridCategoryNameHandler extends MetaclassHandler {
	public DocumentHybridCategoryNameGroup parse(String path) throws FileNotFoundException {

		CategoryStore categoryStore = CategoryStore.parse(path);
		TableStore tableStore = TableStore.parse(path);

		DocumentHybridCategoryNameGroup group = new DocumentHybridCategoryNameGroup(path, categoryStore,
				tableStore);
		return group;
	}
}
