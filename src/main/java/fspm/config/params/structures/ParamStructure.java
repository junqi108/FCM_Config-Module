package fspm.config.params.structures;

/**
 * Represents a structure found in ParamGroups that store parameters.
 */
public abstract class ParamStructure {
    private String groupKey;

    public ParamStructure(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGroupKey() {
        return groupKey;
    }
}
