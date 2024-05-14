package fspm.config.params.group;

import fspm.config.params.hierarchy.CategoryHierarchy;

public class DocumentCategoryNameGroup extends ParamGroup {
	private CategoryHierarchy categoryHierarchy;

	public DocumentCategoryNameGroup(String key, CategoryHierarchy categoryHierarchy) {
		super(key);
		this.categoryHierarchy = categoryHierarchy;
	}

	public CategoryHierarchy getCategoryHierarchy() {
		return categoryHierarchy;
	}
}
