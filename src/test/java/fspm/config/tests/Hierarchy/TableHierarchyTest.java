package fspm.config.tests.Hierarchy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fspm.config.params.ParamTable;
import fspm.config.params.group.DocumentHybridCategoryNameGroup;
import fspm.config.params.hierarchy.TableHierarchy;
import fspm.config.tests.ParamAccessTestSuite;
import static fspm.config.ConfigTestSuite.*;

public class TableHierarchyTest {
    @Before
    public void reset() {
        CONFIG.reset();
        ParamAccessTestSuite.addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testGetTable() {
        TableHierarchy hierarchy = CONFIG.getGroup("hybrid-format",
                DocumentHybridCategoryNameGroup.class).getTableHierarchy();

        ParamTable soilPhysicalProperties = hierarchy.getTable("soilPhysicalProperties");
        ParamTable soilChemicalProperties = hierarchy.getTable("soilChemicalProperties");

        for (int i = 1; i <= 4; i++) {
            println(soilPhysicalProperties.getInteger("layer_" + i, "layerThickness")); // indexing using row, col
        }
        Assert.assertEquals(0.0, soilChemicalProperties.getDouble("layer_1", "absorptionCoefficient"), 0);
    }
}
