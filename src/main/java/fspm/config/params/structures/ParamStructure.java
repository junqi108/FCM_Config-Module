package fspm.config.params.structures;

/**
 * Represents a structure found in ParamGroups that store parameters.
 */
public abstract class ParamStructure {
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
