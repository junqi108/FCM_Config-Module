package fspm.config.params.group;

import java.util.HashMap;
import java.util.Map;

import fspm.config.params.ParamCategory;
import fspm.util.exceptions.KeyConflictException;
import fspm.util.exceptions.KeyNotFoundException;

public class DocumentCategoryNameGroup extends ParamGroup {

	/**
     * List of parameter categories stored as {@link ParamCategory} instances.
     */
    private Map<String, ParamCategory> categories;

	public DocumentCategoryNameGroup(String key) {
		super(key);
        categories = new HashMap<>();
	}

	/**
     * Adds a new category.
     * 
     * @param category {@link ParamCategory} to be added.
     * @throws KeyConflictException     If there is already a category with 
     *                                  the same key.
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
     * @throws KeyNotFoundException    If the category with the given key 
     *                                 could not be found.
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
 				category.get(paramKey);
 				return category;
 			} catch (KeyNotFoundException e) {
 				// This category does not contain the key
 				continue;
 			}
 		}
    	throw new KeyNotFoundException(paramKey, 
				String.format("Could not find parameter '%s' in any category within group '%s'", paramKey, getKey()));
    }
 		
    
    @Override
    public String toString() {
    	StringBuilder string = new StringBuilder();
    	for (ParamCategory category : categories.values()) {
    		string.append(category);
    	}
    	return string.toString();
    }
}
