package fspm.config.tests.Structure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fspm.config.params.ParamTable;
import fspm.config.params.groups.DocumentHybridCategoryNameGroup;
import fspm.config.params.structures.TableStore;
import static fspm.config.ConfigTestSuite.*;

public class TableStoreTest {
    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testGetTable() {
        TableStore store = CONFIG.getGroup("hybrid-format",
                DocumentHybridCategoryNameGroup.class).getTableStore();

        ParamTable soilPhysicalProperties = store
                .getTable("soilPhysicalProperties");
        ParamTable soilChemicalProperties = store
                .getTable("soilChemicalProperties");

        for (int i = 1; i <= 4; i++) {
            println(soilPhysicalProperties.getInteger("layer_" + i,
                    "layerThickness")); // indexing using row, col
        }
        Assert.assertEquals(0.0, soilChemicalProperties.getDouble("layer_1",
                "absorptionCoefficient"), 0);
    }
}
