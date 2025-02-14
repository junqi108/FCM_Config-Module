package io.github.fruitcropxl.config.params.structures;

import java.io.FileNotFoundException;
import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import io.github.fruitcropxl.config.adapters.JsonFileReader;
import io.github.fruitcropxl.config.params.KeyParamAccessor;
import io.github.fruitcropxl.config.params.ParamCategory;
import io.github.fruitcropxl.config.params.ParamFactory;
import io.github.fruitcropxl.config.params.Parameter;
import io.github.fruitcropxl.config.util.exceptions.KeyConflictException;
import io.github.fruitcropxl.config.util.exceptions.KeyNotFoundException;

/**
 * Represents the collection of categories in a document.
 */
public class CategoryStore extends ParamStructure implements KeyParamAccessor {

    public static final String CATEGORIES_HEADER = "category";

    private ParamCategory categoryContext;

    /**
     * List of parameter categories stored as {@link ParamCategory} instances.
     */
    private Map<String, ParamCategory> categories;

    /**
     * If parameters are stored in a flat structure, get and set parameter methods will seek out the
     * first occurrence of the given parameter key.
     * <p>
     * Warning: this means duplicate parameter keys may result in either parameter being chosen
     * unpredictably.
     */
    public boolean useFlattenedCategories;

    public CategoryStore(String groupKey) {
        super(groupKey);
        categories = new HashMap<>();
        useFlattenedCategories = false;
    }

    public static CategoryStore parse(String path)
            throws FileNotFoundException {
        JsonNode tree = JsonFileReader.getTreeFromFile(path);

        CategoryStore store = new CategoryStore(path);

        ParamFactory paramFactory = new ParamFactory();

        JsonNode categoriesNode = tree.get(CATEGORIES_HEADER);
        Iterator<String> categoryNames = categoriesNode.fieldNames();

        // Parse each category node
        for (JsonNode categoryNode : categoriesNode) {
            String categoryName = categoryNames.next().toString();
            ParamCategory category = new ParamCategory(categoryName);

            Iterator<String> paramKeys = categoryNode.fieldNames();

            // Parse each parameter node
            for (JsonNode paramNode : categoryNode) {
                Parameter param = paramFactory
                        .parseParameter(paramKeys.next().toString(), paramNode);

                // null if paramNode type is unsupported
                // TODO: use checked exception for UnsupportedOperationException
                if (param != null) {
                    category.addParameter(param);
                }
            }
            store.addCategory(category);
        }
        return store;
    }

    /**
     * Adds a new category.
     * 
     * @param category {@link ParamCategory} to be added.
     * @throws KeyConflictException If there is already a category with the same key.
     */
    public void addCategory(ParamCategory category) {
        // Use category key as unique identifier
        if (categories.containsKey(category.getKey())) {
            throw new KeyConflictException(category.getKey());
        } else {
            categories.put(category.getKey(), category);
        }
    }

    /**
     * Gets the category with the given key.
     * 
     * @param key The category key.
     * @return The {@link ParamCategory} with the given key.
     * @throws KeyNotFoundException If the category with the given key could not be found.
     */
    public ParamCategory getCategory(String key) {
        ParamCategory category = categories.get(key);

        if (category != null) {
            return category;
        }
        throw new KeyNotFoundException(key, "Could not find category");
    }

    /**
     * @return The first category containing the parameter with the given key.
     */
    public ParamCategory getCategoryWithParam(String paramKey) {
        for (ParamCategory category : categories.values()) {
            try {
                category.getParameter(paramKey);
                return category;
            } catch (KeyNotFoundException e) {
                // This category does not contain the key
                continue;
            }
        }
        throw new KeyNotFoundException(paramKey, String.format(
                "Could not find parameter '%s' in any category within group '%s'",
                paramKey, super.getGroupKey()));
    }

    /**
     * Sets the category context for the CategoryStore. Category context refers to selecting a category
     * when accessing parameters without needing to retrieve them through
     * {@link io.github.fruitcropxl.config.params.ParamCategory}. Note that this only works when
     * useFlattenedCategories is true.
     * 
     * @param key The key if the category to set as the context.
     * @return This CategoryStore with the given category context set.
     */
    public CategoryStore setCategoryContext(String key) {
        try {
            categoryContext = getCategory(key);
        } catch (KeyNotFoundException e) {
            throw new KeyNotFoundException(key,
                    "Could not set category context as category with key does not exist.");
        }
        return this;
    }

    private void validateFlattenedAccess(String key) {
        // Check: if using flattened categories, then check if key exists in current
        // category. Else, find category and set as context
        // If not using flattened categories, check if categorycontext exists

        if (useFlattenedCategories) {
            // Check if current category contains key.
            if (categoryContext != null) {
                try {
                    categoryContext.getParameter(key);
                    // No exception; found key; continue as normal
                    return;
                } catch (KeyNotFoundException e) {
                    // Cannot find key; search other categories.
                }
            }

            /**
             * Directly set category context, as no need to search again with setCategoryContext.
             * 
             * FIXME: getCategoryWithParam(key) may be acting as a middle-man
             * 
             * Purpose: to find the category containing the key and set as the categoryContext, such that
             * getters can utilise type specific getBoolean (etc) methods to get the parameter.
             * 
             * Could be improved using Java Generics.
             */
            categoryContext = getCategoryWithParam(key);
        } else {
            if (categoryContext == null) {
                throw new NullPointerException(
                        "Please set a category context to access parameters.");
            }
        }
    }

    // Access methods for when flattened categories is enabled.

    @Override
    public <T> T get(String key, Class<T> type) {
        validateFlattenedAccess(key);
        return categoryContext.get(key, type);
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        validateFlattenedAccess(key);
        return categoryContext.get(key, type, defaultValue);
    }

    @Override
    public <T> T[] getArray(String key, Class<T[]> type) {
        validateFlattenedAccess(key);
        return categoryContext.getArray(key, type);
    }

    @Override
    public <T> T[] getArray(String key, Class<T[]> type, T[] defaultValue) {
        validateFlattenedAccess(key);
        return categoryContext.getArray(key, type, defaultValue);
    }

    public void set(String key, boolean value) {
        validateFlattenedAccess(key);
        categoryContext.setParameter(key,
                new ParamFactory().createParameter(key, value));
    }

    public void set(String key, String value) {
        validateFlattenedAccess(key);
        categoryContext.setParameter(key,
                new ParamFactory().createParameter(key, value));
    }

    public void set(String key, int value) {
        validateFlattenedAccess(key);
        categoryContext.setParameter(key,
                new ParamFactory().createParameter(key, value));
    }

    public void set(String key, double value) {
        validateFlattenedAccess(key);
        categoryContext.setParameter(key,
                new ParamFactory().createParameter(key, value));
    }

    public void set(String key, Double[] value) {
        validateFlattenedAccess(key);
        categoryContext.setParameter(key,
                new ParamFactory().createParameter(key, value));
    }

    // public <T> void set(String key, T value) {
    // validateFlattenedAccess(key);
    // categoryContext.setParameter(key, new ParamFactory().createParameter(key,
    // value));
    // }

    public boolean isNull(String key) {
        validateFlattenedAccess(key);
        return categoryContext.isNull(key);
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (ParamCategory category : categories.values()) {
            string.append(category);
        }
        return string.toString();
    }
}
