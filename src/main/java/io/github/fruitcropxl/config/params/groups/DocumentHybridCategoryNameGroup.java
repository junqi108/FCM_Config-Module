package io.github.fruitcropxl.config.params.groups;

import java.io.FileNotFoundException;

import io.github.fruitcropxl.config.params.structures.CategoryStore;
import io.github.fruitcropxl.config.params.structures.TableStore;

/**
 * Represents and encapsulates access for the document-hybrid-category-name format. This format
 * contains a Category and Table structure.
 */
public class DocumentHybridCategoryNameGroup extends ParamGroup {

    private CategoryStore categoryStore;
    private TableStore tableStore;

    public DocumentHybridCategoryNameGroup(String key,
            CategoryStore categoryStore, TableStore tableStore) {
        super(key);
        this.categoryStore = categoryStore;
        this.tableStore = tableStore;
    }

    public static DocumentHybridCategoryNameGroup parse(String path)
            throws FileNotFoundException {

        CategoryStore categoryStore = CategoryStore.parse(path);
        TableStore tableStore = TableStore.parse(path);

        DocumentHybridCategoryNameGroup group = new DocumentHybridCategoryNameGroup(
                path, categoryStore, tableStore);
        return group;
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
