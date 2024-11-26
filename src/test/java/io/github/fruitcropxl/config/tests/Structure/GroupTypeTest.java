package io.github.fruitcropxl.config.tests.Structure;

import static io.github.fruitcropxl.config.ConfigTestSuite.*;

import org.junit.Before;
import org.junit.Test;

import io.github.fruitcropxl.config.params.groups.DocumentCategoryNameGroup;
import io.github.fruitcropxl.config.params.groups.DocumentHybridCategoryNameGroup;

public class GroupTypeTest {
    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testGroupTypes() {
        DocumentCategoryNameGroup group = CONFIG.getGroup(
                "model.input.data.default", DocumentCategoryNameGroup.class);
        println(group);

        DocumentHybridCategoryNameGroup hybridGroup = CONFIG.getGroup(
                "hybrid-format", DocumentHybridCategoryNameGroup.class);
        println(hybridGroup);

        // ParamTable soilPhysicalProperties =
        // hybridGroup.getTableStore().getTable("soilPhysicalProperties");
        // println(soilPhysicalProperties.getValue("layer_1",
        // "layerThickness"));
    }
}
