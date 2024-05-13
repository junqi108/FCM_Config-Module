package fspm.config.params.group;

import java.lang.StringBuilder;

import java.util.Map;
import java.util.HashMap;

import fspm.config.params.ParamCategory;
import fspm.util.exceptions.KeyConflictException;
import fspm.util.exceptions.KeyNotFoundException;

/**
 * Parameter configuration class for storing parameter categories.
 * 
 * @author Ou-An Chuang
 */
public class ParamGroup {
	private String key;
	
    /**
     * Class constructor.
     * <p>
     * All initialisations go here.
     */
    public ParamGroup(String key) {
    	this.key = key;
    }
    
    public String getKey() {
    	return key;
    }
}
