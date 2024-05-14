package fspm.config.params.group;

import java.util.List;

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

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Group: " + getKey() + "\n");
		string.append("Categories: " + categoryHierarchy);

		return string.toString();
	}
}
