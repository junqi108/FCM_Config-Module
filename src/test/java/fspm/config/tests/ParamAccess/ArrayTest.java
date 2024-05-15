package fspm.config.tests.ParamAccess;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;
import fspm.config.tests.ParamAccessTestSuite;
import fspm.util.exceptions.TypeNotFoundException;
import static fspm.config.ConfigTestSuite.*;

public class ArrayTest {

    @Before
    public void reset() {
        CONFIG.reset();
        ParamAccessTestSuite.addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testGetArrays() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("soilParams_pot_1",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();
        hierarchy.useFlattenedCategories = true;

        println(hierarchy.getArray("layerThickness", Integer[].class)[0]);
        println(hierarchy.getIntegerArray("layerThickness")[0]);
    }

    @Test
    // @Ignore
    public void testIncorrectArrayType() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("soilParams_pot_1",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();
        hierarchy.useFlattenedCategories = true;

        try {
            println(hierarchy.getArray("layerThickness", Double[].class)[0]);
        } catch (TypeNotFoundException e) {
            return;
        }
        fail("Should have thrown TypeNotFoundException as layerThickness is an Integer[]");

        try {
            println(hierarchy.getDoubleArray("layerThickness")[0]);
        } catch (TypeNotFoundException e) {
            return;
        }
        fail("Should have thrown TypeNotFoundException as layerThickness is an Integer[]");
    }

    @Test
    // @Ignore
    public void testPhenology() {
        CategoryHierarchy hierarchy = CONFIG
                .getGroup("phenology.parameters.SauvignonBlanc",
                        DocumentCategoryNameGroup.class)
                .getCategoryHierarchy()
                .setCategoryContext("parameters");

        println(Arrays.toString(hierarchy.getArray("BUDBURST_CANE_DIFF", Double[].class)));
    }
}
