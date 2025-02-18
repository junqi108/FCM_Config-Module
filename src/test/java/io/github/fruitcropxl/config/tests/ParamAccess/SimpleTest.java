package io.github.fruitcropxl.config.tests.ParamAccess;

import static io.github.fruitcropxl.config.ConfigTestSuite.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.fruitcropxl.config.params.ParamCategory;
import io.github.fruitcropxl.config.params.groups.DocumentCategoryNameGroup;
import io.github.fruitcropxl.config.params.structures.CategoryStore;
import io.github.fruitcropxl.config.util.exceptions.KeyNotFoundException;

public class SimpleTest {

    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testAccessExamples() {
        // Full descriptive access of store
        println(" ===== Full descriptive access ");

        println(CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class));

        println(CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("Boolean_variables")
                .get("useStaticArc", Boolean.class));
        println(CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("Boolean_variables")
                .get("inputLeafN", Boolean.class));

        println(CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("simulation_location")
                .get("location_name", String.class));

        /**
         * Contextual access
         *
         * - Localises the selected category context to a local ParamGroup variable
         */

        println(" ===== Contextual access ");

        CategoryStore store = CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().setCategoryContext("Boolean_variables");

        println(store.get("useStaticArc", Boolean.class));
        println(store.get("inputLeafN", Boolean.class));

        store.setCategoryContext("simulation_location");

        println(store.get("location_name", String.class));

        // Access via aliasing

        println(" ===== Aliasing ");

        ParamCategory booleans = CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("Boolean_variables");

        println(booleans.get("useStaticArc", Boolean.class));
        println(booleans.get("inputLeafN", Boolean.class));

        ParamCategory simulationLocation = CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("simulation_location");

        println(simulationLocation.get("location_name", String.class));
    }

    @Test
    // @Ignore
    public void testNull() {
        CategoryStore store = CONFIG
                .getGroup("model.input.data.default",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(null, store.get("BIOMASS_BERRY", Double.class));
        Assert.assertEquals(null, store.get("BIOMASS_WOOD", String.class));
    }

    @Test
    // @Ignore
    public void testNullWithDefaultValue() {
        CategoryStore store = CONFIG
                .getGroup("model.input.data.default",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(1, store.get("BIOMASS_BERRY", Double.class, 1.0),
                0);
    }

    @Test
    // @Ignore
    public void testTypes() {
        CategoryStore store = CONFIG
                .getGroup("group", DocumentCategoryNameGroup.class)
                .getCategoryStore().setCategoryContext("category");

        println(store.get("doubleParam", Double.class));
        println(store.get("floatParam", Double.class));

        println(store.get("nullParam", Double.class) == null);
        println(store.get("nullParam", String.class) == null);
        println(store.get("nullParam", Integer.class) == null);
        println(store.get("nullParam", Double.class) == null);

        println(store.isNull("nullParam"));
    }

    @Test
    // @Ignore
    public void testDefault() {
        println(CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class));

        CategoryStore store = CONFIG
                .getGroup("model.input.data.default",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore()
                .setCategoryContext("initial_condition_biomass");

        println(store.get("BIOMASS_LEAF", Double.class) == null);
    }

    @Test
    // @Ignore
    public void testCrossCategoryAccess() {
        CategoryStore store = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class).getCategoryStore();

        store.setCategoryContext("module_configuration");

        Assert.assertEquals(null, store.get("special_scenario", Boolean.class));

        store.setCategoryContext("model_functionality");

        Assert.assertTrue(store.get("calcLightInterception", Boolean.class));

        try {
            Assert.assertEquals(0,
                    store.get("trainingSystem", Integer.class).intValue());
            Assert.fail(
                    "Should have thrown KeyNotFoundException as trainingSystem is not in the model_functionality category");
        } catch (KeyNotFoundException e) {
        }
    }

    @Test
    // @Ignore
    public void testFlatCategories() {
        CategoryStore store = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class).getCategoryStore();
        store.setFlattenedAccess(true);

        println(store.get("useStaticArc", Boolean.class));
        println(store.get("FractionDiffuseLightDaily", Double.class));

        println(store.get("radiationControl", Integer.class));
        println(store.get("REFTMP", Double.class));

        println(store.get("rootArchitecture_file", String.class));
    }
}
