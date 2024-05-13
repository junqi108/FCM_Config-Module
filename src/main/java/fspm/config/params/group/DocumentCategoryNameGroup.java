package fspm.config.params.group;

import java.util.HashMap;
import java.util.Map;

import fspm.config.params.ParamAccessor;
import fspm.config.params.ParamCategory;
import fspm.util.exceptions.KeyConflictException;
import fspm.util.exceptions.KeyNotFoundException;

public class DocumentCategoryNameGroup extends ParamGroup implements ParamAccessor {

    private ParamCategory categoryContext; 

	/**
     * List of parameter categories stored as {@link ParamCategory} instances.
     */
    private Map<String, ParamCategory> categories;

    /**
     * If parameters are stored in a flat structure, get and set parameter methods will
     * seek out the first occurrence of the given parameter key.
     *<p>
     * Warning: this means duplicate parameter keys may result in either parameter
     * being chosen unpredictably.
     */
    public boolean useFlattenedCategories;


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

    public DocumentCategoryNameGroup getCategoryContext(String key) {
    	try {
    		categoryContext = getCategory(key);
    	} catch (KeyNotFoundException e) {
    		throw new KeyNotFoundException(key, "Could not set category context as category with key does not exist.");
    	}
    	return this;
    }

    private void validateFlattenedAccess(String key) {
        // if (!useFlattenedCategories) { // TODO: check if using flattened categories
        //     throw new
        // }
    	// Check if key exists in current category. Else, find category and set as context

        // Check if current category contains key.
        if (categoryContext != null) {
            try {
                categoryContext.get(key);
                // No exception; found key; continue as normal
                return;
            } catch (KeyNotFoundException e) {
                // Cannot find key; search other categories.
            }
        }
        
        /** 
         * Directly set category context, as no need to search again with {@link #getCategoryContext(String)}.
         * FIXME: getCategoryWithParam(key) may be acting as a middle-man
         * 
         * Purpose: to find the category containing the key and set as the categoryContext, such that getters can
         * utilise type specific getBoolean (etc) methods to get the parameter.
         * 
         * Could be improved using Java Generics.
         */
        categoryContext = getCategoryWithParam(key);
        return;
    }

    @Override
	public Boolean getBoolean(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getBoolean(key);
	}

	@Override
	public String getString(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getString(key);
	}

	@Override
	public Integer getInteger(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getInteger(key);
	}

	@Override
	public Double getDouble(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getDouble(key);
	}
	
	@Override
	public Integer[] getIntegerArray(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getIntegerArray(key);
	}

	@Override
	public Double[] getDoubleArray(String key) {
		validateFlattenedAccess(key);
		return categoryContext.getDoubleArray(key);
	}
	
	
	
	public boolean getBoolean(String key, boolean defaultValue) {
		Boolean value = getBoolean(key);
		return value != null ? value : defaultValue;
	}
	
	public String getString(String key, String defaultValue) {
		String value = getString(key);
		return value != null ? value : defaultValue;
	}
	
	public int getInteger(String key, int defaultValue) {
		Integer value = getInteger(key);
		return value != null ? value : defaultValue;
	}
	
	public double getDouble(String key, double defaultValue) {
		Double value = getDouble(key);
		return value != null ? value : defaultValue;
	}
	
	public double[] getDoubleArray(String key, double[] defaultValue) {
		Double[] storedValue = getDoubleArray(key);
		
		// Convert from Double[] to double[]
		double[] value = new double[storedValue.length];
		for (int i = 0; i < storedValue.length; i++) {
			value[i] = storedValue[i];
		}
		
		return value != null ? value : defaultValue;
	}
	

	
	
	@Override
	public void set(String key, boolean value) {
		validateFlattenedAccess(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, String value) {
		validateFlattenedAccess(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, int value) {
		validateFlattenedAccess(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, double value) {
		validateFlattenedAccess(key);
		categoryContext.set(key, value);
	}
	

	@Override
	public void set(String key, Double[] value) {
		validateFlattenedAccess(key);
		categoryContext.set(key, value);
	}
	
	@Override
	public boolean isNull(String key) {
		validateFlattenedAccess(key);
		return categoryContext.isNull(key);
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
