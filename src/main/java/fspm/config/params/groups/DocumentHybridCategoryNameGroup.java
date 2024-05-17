package fspm.config.params.groups;

import fspm.config.params.structures.CategoryStore;
import fspm.config.params.structures.TableStore;

public class DocumentHybridCategoryNameGroup extends ParamGroup {

	private CategoryStore categoryStore;
	private TableStore tableStore;

	public DocumentHybridCategoryNameGroup(String key, CategoryStore categoryStore,
			TableStore tableStore) {
		super(key);
		this.categoryStore = categoryStore;
		this.tableStore = tableStore;
	}

	public CategoryStore getCategoryStore() {
		return categoryStore;
	}

	public TableStore getTableStore() {
		return tableStore;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Group: " + getKey() + "\n");
		string.append("Categories: " + categoryStore);
		string.append("Tables: " + tableStore);

		return string.toString();
	}
}
