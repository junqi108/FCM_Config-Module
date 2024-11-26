package io.github.fruitcropxl.config.params.groups;

import java.io.FileNotFoundException;

import io.github.fruitcropxl.config.params.structures.CategoryStore;

/**
 * Represents and encapsulates access for the document-category-name format. This format contains a
 * Category structure.
 */
public class DocumentCategoryNameGroup extends ParamGroup {
    private CategoryStore categoryStore;

    public DocumentCategoryNameGroup(String key, CategoryStore categoryStore) {
        super(key);
        this.categoryStore = categoryStore;
    }

    public static DocumentCategoryNameGroup parse(String path)
            throws FileNotFoundException {
        CategoryStore store = CategoryStore.parse(path);

        DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(path,
                store);
        return group;
    }

    public CategoryStore getCategoryStore() {
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
