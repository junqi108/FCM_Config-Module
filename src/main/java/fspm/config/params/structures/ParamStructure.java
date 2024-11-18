package fspm.config.params.structures;

import java.io.Serializable;

/**
 * Represents a structure found in ParamGroups that store parameters.
 */
public abstract class ParamStructure implements Serializable {
    private String groupKey;

    /**
     * @param groupKey The key of the group this structure belongs to.
     */
    public ParamStructure(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGroupKey() {
        return groupKey;
    }
}
