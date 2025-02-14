package io.github.fruitcropxl.config.tests.ParamAccess;

import static io.github.fruitcropxl.config.ConfigTestSuite.*;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import io.github.fruitcropxl.config.params.ParamCategory;
import io.github.fruitcropxl.config.params.groups.DocumentCategoryNameGroup;
import io.github.fruitcropxl.config.params.structures.CategoryStore;
import io.github.fruitcropxl.config.util.exceptions.TypeNotFoundException;

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
    }

    @Test
    // @Ignore
    public void testGetDoubleArrayCases() {
        CategoryStore store = CONFIG
                .getGroup("paramSetTest", DocumentCategoryNameGroup.class)
                .getCategoryStore();

        ParamCategory arrays = store.getCategory("arrays");
        println(Arrays
                .toString(arrays.getArray("doubleArray", Double[].class)));
        println(Arrays.toString(arrays.getArray("intArray", Double[].class)));
        println(Arrays.toString(arrays.getArray("floatArray", Double[].class)));
        println(Arrays.toString(arrays.getArray("mixedArray", Double[].class)));

        try {
            println(Arrays
                    .toString(arrays.getArray("invalidArray", Double[].class)));
            fail("Should have failed as invalidArray contains a non-double value at index 0");
        } catch (TypeNotFoundException e) {
        }

        try {
            println(Arrays.toString(
                    arrays.getArray("invalidMixedArray", Double[].class)));
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
            println(store.getArray("layerThickness", Boolean[].class)[0]);
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
