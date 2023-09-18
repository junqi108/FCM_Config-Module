package fspm.config;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import fspm.config.adapters.ConfigAdapter;
import fspm.config.params.ParamAccessor;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamGroup;
import fspm.util.exceptions.KeyConflictException;
import fspm.util.exceptions.KeyNotFoundException;

/**
 * Configuration class for storing input configurations to be used in simulations.
 * 
 * @author Ou-An Chuang
 */
public class Config implements ParamAccessor {
    /**
     * Singleton instance for the global config. 
     * Set to null by default until instance is first retrieved with {@link #getInstance()}.
     */
    private static Config instance = null;
    
    /**
     * If parameters are stored in a flat structure, get and set parameter methods will
     * seek out the first occurrence of the given parameter key.
     *<p>
     * Warning: this means duplicate parameter keys may result in either parameter
     * being chosen unpredictably.
     */
    public boolean useFlattenedCategories;

    /**
     * We use Map instead of Set to allow us to check for key conflicts.
     */
    private Map<String, ParamGroup> paramGroups;
    
    private ParamGroup groupContext;
    private ParamCategory categoryContext;


    /**
     * Class constructor.
     * Private access as creation should be controlled to enforce singleton pattern
     */
    private Config() {
    	reset();
    }

    /**
     * Gets the singleton instance of the simulation {@link Config}.
     * Creates a new Config if there was no existing instance.
     * 
     * @return Singleton instance of {@link Config}.
     */
    public static Config getInstance() {
        if (instance == null) {
        	instance = new Config();
        }
        return instance;
    }
    
    public void reset() {
    	if (paramGroups == null) {
    		paramGroups = new HashMap<>();
    	} else {
        	paramGroups.clear();
    	}
    	useFlattenedCategories = false;
    	groupContext = null;
    	categoryContext = null;
    }


    public void addGroup(ConfigAdapter adapter) throws FileNotFoundException {
    	addGroup(adapter.parse());
    }
    public void addGroup(String key, ConfigAdapter adapter) throws FileNotFoundException {
    	addGroup(key, adapter.parse());
    }
    
    /**
     * Add a new group with a unique key.
     * @param group
     */
    public void addGroup(ParamGroup group) {
        addGroup(group.getKey(), group);
    }
    
    private void addGroup(String key, ParamGroup group) {
    	if (paramGroups.containsKey(key)) {
    		throw new KeyConflictException(key);
    	}
    	paramGroups.put(key, group);
    }
    
    /**
     * Gets the parameter group with the given key.
     * @return Parameter group
     */
    public ParamGroup getGroup(String key) {
    	ParamGroup group = paramGroups.get(key);
    	
    	if (group == null) {
    		throw new KeyNotFoundException(key);
    	}
    	return group;
    }
    
    /**
     * Remove the parameter group with the given key.
     * @param key
     * @return True if group exists; false otherwise.
     */
    public boolean removeGroup(String key) {
		ParamGroup group = paramGroups.get(key);
    	
    	if (group == null) {
    		return false;
    	}
    	paramGroups.remove(key);
    	return true;
    }
    
    
    
    
    public Config setGroupContext(String key) {
    	try {
    		groupContext = getGroup(key);
    	} catch (KeyNotFoundException e) {
    		throw new KeyNotFoundException(key, "Could not set group context as group with key does not exist.");
    	}
    	return this;
    }
    
    public Config setCategoryContext(String key) {
    	checkGroupContextExists();
		
    	try {
    		categoryContext = groupContext.getCategory(key);
    	} catch (KeyNotFoundException e) {
    		throw new KeyNotFoundException(key, "Could not set category context as category with key does not exist.");
    	}
    	return this;
    }
    
    
    
    
    
    private void checkGroupContextExists() {
    	if (groupContext == null) {
    		throw new NullPointerException("Please set a group context to access parameter categories.");
    	}
    }
    
    private boolean isCategoryContextExists() {
    	return categoryContext != null;
    }
    
    private void checkContextsExists(String key) {
    	checkGroupContextExists();


    	// Check: if using flattened categories, then check if key exists in current category. Else, find category and set as context
    	// If not using flattened categories, check if categorycontext exists
    	
    	if (useFlattenedCategories) {
        	// Check if current category contains key.
    		if (isCategoryContextExists()) {
    			try {
        			categoryContext.get(key);
        			// No exception; found key; continue as normal
        			return;
        		} catch (KeyNotFoundException e) {
        			// Cannot find key; search other categories.
        		}
    		}
    		
    		/** 
        	 * Directly set category context, as no need to search again with {@link #setCategoryContext(String)}.
        	 * FIXME: getCategoryWithParam(key) may be acting as a middle-man
        	 * 
        	 * Purpose: to find the category containing the key and set as the categoryContext, such that getters can
        	 * utilise type specific getBoolean (etc) methods to get the parameter.
        	 * 
        	 * Could be improved using Java Generics.
        	 */
    		categoryContext = groupContext.getCategoryWithParam(key);
    		return;
    	}
    }
    

	@Override
	public Boolean getBoolean(String key) {
		checkContextsExists(key);
		return categoryContext.getBoolean(key);
	}

	@Override
	public String getString(String key) {
		checkContextsExists(key);
		return categoryContext.getString(key);
	}

	@Override
	public Integer getInteger(String key) {
		checkContextsExists(key);
		return categoryContext.getInteger(key);
	}

	@Override
	public Double getDouble(String key) {
		checkContextsExists(key);
		return categoryContext.getDouble(key);
	}

	@Override
	public Double[] getDoubleArray(String key) {
		checkContextsExists(key);
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
		checkContextsExists(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, String value) {
		checkContextsExists(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, int value) {
		checkContextsExists(key);
		categoryContext.set(key, value);
	}

	@Override
	public void set(String key, double value) {
		checkContextsExists(key);
		categoryContext.set(key, value);
	}
	

	@Override
	public void set(String key, Double[] value) {
		checkContextsExists(key);
		categoryContext.set(key, value);
	}
	
	@Override
	public boolean isNull(String key) {
		checkContextsExists(key);
		return categoryContext.isNull(key);
	}
}
