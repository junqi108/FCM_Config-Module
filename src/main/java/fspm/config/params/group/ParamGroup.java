package fspm.config.params.group;

import fspm.util.KeyElement;

/**
 * Parameter configuration class for storing parameter categories.
 * 
 * @author Ou-An Chuang
 */
public abstract class ParamGroup extends KeyElement {
    /**
     * Class constructor.
     * <p>
     * All initialisations go here.
     */
    protected ParamGroup(String key) {
    	super(key);
    }
}
