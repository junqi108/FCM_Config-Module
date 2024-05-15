package fspm.config.params.hierarchy;

public abstract class Hierarchy {
    private String groupKey;

    public Hierarchy(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGroupKey() {
        return groupKey;
    }
}
