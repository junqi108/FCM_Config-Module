package fspm.config.tests.ParamAccess;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;
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
        CategoryStore store = CONFIG
                .getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();
        store.useFlattenedCategories = true;

        println(store.getArray("layerThickness", Integer[].class)[0]);
        println(store.getIntegerArray("layerThickness")[0]);
    }

    @Test
    // @Ignore
    public void testIncorrectArrayType() {
        CategoryStore store = CONFIG
                .getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();
        store.useFlattenedCategories = true;

        try {
            println(store.getArray("layerThickness", Double[].class)[0]);
        } catch (TypeNotFoundException e) {
            return;
        }
        fail("Should have thrown TypeNotFoundException as layerThickness is an Integer[]");

        try {
            println(store.getDoubleArray("layerThickness")[0]);
        } catch (TypeNotFoundException e) {
            return;
        }
        fail("Should have thrown TypeNotFoundException as layerThickness is an Integer[]");
    }

    @Test
    // @Ignore
    public void testPhenology() {
        CategoryStore store = CONFIG
                .getGroup("phenology.parameters.SauvignonBlanc",
                        DocumentCategoryNameGroup.class)
                .getCategoryHierarchy().setCategoryContext("parameters");

        println(Arrays.toString(
                store.getArray("BUDBURST_CANE_DIFF", Double[].class)));
    }
}
