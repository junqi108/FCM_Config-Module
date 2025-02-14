package io.github.fruitcropxl.config.params;

import java.util.HashMap;
import java.util.Map;

import io.github.fruitcropxl.config.util.KeyElement;
import io.github.fruitcropxl.config.util.exceptions.KeyConflictException;
import io.github.fruitcropxl.config.util.exceptions.KeyNotFoundException;
import io.github.fruitcropxl.config.util.exceptions.TypeNotFoundException;

/**
 * Stores {@link io.github.fruitcropxl.config.params.Parameter Parameters} that belong to a common
 * category. These parameters can be accessed to get or set their values.
 * 
 * @author Ou-An Chuang
 */
public class ParamCategory extends KeyElement implements KeyParamAccessor {
    private Map<String, Parameter> params;

    public ParamCategory(String key) {
        super(key);
        this.params = new HashMap<>();
    }

    /**
     * Adds a new {@link Parameter} to the category.
     * 
     * @param param Parameter to be added to the category.
     * @throws KeyConflictException If there is already a parameter with the same key in the category.
     */
    public void addParameter(Parameter param) {
        // Use parameter key as unique identifier
        if (params.containsKey(param.getKey())) {
            throw new KeyConflictException(param.getKey());
        } else {
            params.put(param.getKey(), param);
        }
    }

    /**
     * Set parameter with the given key to the provided {@link Parameter}. The parameter is added if
     * there was not an existing key to replace.
     * <p>
     * 
     * @param key   The parameter key.
     * @param param {@link Parameter} to replace or add to the category.
     */
    public void setParameter(String key, Parameter param) {
        if (param.getType() != getParameter(key).getType()) {
            // Prevent overriding parameters with a mismatching type.
            // TODO: consider cases where arrays of different types are being replaced
            throw new TypeNotFoundException(key, param.getType().toString());
        }
        params.put(key, param);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return getParameter(key).getValue();
    }

    @Override
    public <T> T get(String key, Class<T> type, T defaultValue) {
        T value = get(key, type);
        return value == null ? defaultValue : value; // Check for null this way rather than isNull to avoid retrieving twice.
    }

    @Override
    public <T> T[] getArray(String key, Class<T[]> type) {
        return getParameter(key).asArray(type);
    }

    @Override
    public <T> T[] getArray(String key, Class<T[]> type, T[] defaultValue) {
        T[] value = getArray(key, type);
        return value == null ? defaultValue : value; // Check for null this way rather than isNull to avoid retrieving twice.
    }

    public boolean isNull(String key) {
        return getParameter(key).isNull();
    }

    /**
     * Get generic {@link Parameter} with the given key. Use this method to check whether a parameter
     * exists in this category.
     * 
     * @param key The parameter key.
     * @return Generic {@link Parameter}.
     * @throws KeyNotFoundException If the given key could not be found.
     */
    public Parameter getParameter(String key) {
        Parameter param = params.get(key);

        if (param == null) {
            throw new KeyNotFoundException(key,
                    String.format(
                            "Could not find parameter '%s' in category '%s'",
                            key, getKey()));
        }
        return param;

    }

    @Override
    public String toString() {
        String format = "Category: " + getKey() + "\n";

        for (Parameter param : params.values()) {
            format += "- " + param + "\n";
        }
        return format;
    }
}
