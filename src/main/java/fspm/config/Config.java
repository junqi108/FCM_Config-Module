package fspm.config;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import fspm.config.adapters.ConfigAdapter;
import fspm.config.params.groups.ParamGroup;
import fspm.util.exceptions.KeyConflictException;
import fspm.util.exceptions.KeyNotFoundException;

/**
 * Configuration class for storing input configurations to be used in simulations.
 * 
 * @author Ou-An Chuang
 */
public class Config {
    /**
     * Singleton instance for the global config. Set to null by default until instance is first
     * retrieved with {@link #getInstance()}.
     */
    private static Config instance = null;

    /**
     * We use Map instead of Set to allow us to check for key conflicts.
     */
    private Map<String, ParamGroup> paramGroups;

    /**
     * Class constructor. Private access as creation should be controlled to enforce singleton pattern
     */
    private Config() {
        reset();
    }

    /**
     * Gets the singleton instance of the simulation {@link Config}. Creates a new Config if there was
     * no existing instance.
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
    }

    public void addGroup(ConfigAdapter adapter) throws FileNotFoundException {
        addGroup(adapter.parse());
    }

    public void addGroup(String key, ConfigAdapter adapter)
            throws FileNotFoundException {
        addGroup(key, adapter.parse());
    }

    /**
     * Add a new group with a unique key.
     * 
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
     * 
     * @return Parameter group
     */

    public <T extends ParamGroup> T getGroup(String key, Class<T> groupClass) {
        ParamGroup group = paramGroups.get(key);

        if (group == null) {
            throw new KeyNotFoundException(key);
        }
        if (!groupClass.isInstance(group)) {
            throw new IllegalArgumentException(
                    "Group is not of the expected type: "
                            + groupClass.getSimpleName());
        }

        return groupClass.cast(group);
    }

    /**
     * Remove the parameter group with the given key.
     * 
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
}
