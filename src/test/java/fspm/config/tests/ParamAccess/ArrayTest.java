package fspm.config.tests.ParamAccess;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import fspm.config.params.ParamCategory;
import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;
import fspm.util.exceptions.TypeNotFoundException;
import static fspm.config.ConfigTestSuite.*;

public class ArrayTest {

    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testGetArrays() {
        CategoryStore store = CONFIG
                .getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class)
                .getCategoryStore();
        store.useFlattenedCategories = true;

        println(store.getArray("layerThickness", Integer[].class)[0]);
        println(store.getIntegerArray("layerThickness")[0]);
    }

    @Test
    // @Ignore
    public void testGetDoubleArrayCases() {
        CategoryStore store = CONFIG
                .getGroup("paramSetTest", DocumentCategoryNameGroup.class)
                .getCategoryStore();

        ParamCategory arrays = store.getCategory("arrays");
        println(Arrays.toString(arrays.getDoubleArray("doubleArray")));
        println(Arrays.toString(arrays.getDoubleArray("intArray")));
        println(Arrays.toString(arrays.getDoubleArray("floatArray")));
        println(Arrays.toString(arrays.getDoubleArray("mixedArray")));

        try {
            println(Arrays.toString(arrays.getDoubleArray("invalidArray")));
            fail("Should have failed as invalidArray contains a non-double value at index 0");
        } catch (TypeNotFoundException e) {
        }

        try {
            println(Arrays
                    .toString(arrays.getDoubleArray("invalidMixedArray")));
            fail("Should have failed as invalidMixedArray contains non-double value(s)");
        } catch (UnsupportedOperationException e) {
        }

    }

    @Test
    // @Ignore
    public void testIncorrectArrayType() {
        CategoryStore store = CONFIG
                .getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class)
                .getCategoryStore();
        store.useFlattenedCategories = true;

        try {
            println(store.getBooleanArray("layerThickness")[0]);
        } catch (TypeNotFoundException e) {
            println(e);
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
                .getCategoryStore().setCategoryContext("parameters");

        println(Arrays.toString(
                store.getArray("BUDBURST_CANE_DIFF", Double[].class)));
    }
}
