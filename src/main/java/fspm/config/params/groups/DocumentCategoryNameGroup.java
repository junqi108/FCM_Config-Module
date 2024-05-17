package fspm.config.params.groups;

import fspm.config.params.structures.CategoryStore;

public class DocumentCategoryNameGroup extends ParamGroup {
	private CategoryStore categoryStore;

	public DocumentCategoryNameGroup(String key, CategoryStore categoryStore) {
		super(key);
		this.categoryStore = categoryStore;
	}

	public CategoryStore getCategoryHierarchy() {
		return categoryStore;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Group: " + getKey() + "\n");
		string.append("Categories: " + categoryStore);

		return string.toString();
	}
}
