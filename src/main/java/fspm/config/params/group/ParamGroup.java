package fspm.config.params.group;

/**
 * Parameter configuration class for storing parameter categories.
 * 
 * @author Ou-An Chuang
 */
public abstract class ParamGroup {
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
