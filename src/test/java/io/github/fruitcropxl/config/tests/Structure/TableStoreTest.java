package io.github.fruitcropxl.config.tests.Structure;

import static io.github.fruitcropxl.config.ConfigTestSuite.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.fruitcropxl.config.params.ParamTable;
import io.github.fruitcropxl.config.params.groups.DocumentHybridCategoryNameGroup;
import io.github.fruitcropxl.config.params.structures.TableStore;

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
            println(soilPhysicalProperties.get("layer_" + i, "layerThickness",
                    Integer.class)); // indexing using row, col
        }
        Assert.assertEquals(0.0, soilChemicalProperties.get("layer_1",
                "absorptionCoefficient", Double.class), 0);
    }
}
