package fspm.config.params.group;

import fspm.config.params.hierarchy.CategoryHierarchy;
import fspm.config.params.hierarchy.TableHierarchy;

public class DocumentHybridCategoryNameGroup extends ParamGroup {

	private CategoryHierarchy categoryHierarchy;
	private TableHierarchy tableHierarchy;

	public DocumentHybridCategoryNameGroup(String key, CategoryHierarchy categoryHierarchy, TableHierarchy tableHierarchy) {
		super(key);
		this.categoryHierarchy = categoryHierarchy;
		this.tableHierarchy = tableHierarchy;
	}

	public CategoryHierarchy getCategoryHierarchy() {
		return categoryHierarchy;
	}
	public TableHierarchy getTableHierarchy() {
		return tableHierarchy;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Group: " + getKey() + "\n");
		string.append("Categories: " + categoryHierarchy);
		string.append("Tables: " + tableHierarchy);

		return string.toString();
	}
}
