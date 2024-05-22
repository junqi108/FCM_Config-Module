package fspm.config.tests.ParamAccess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fspm.config.params.ParamCategory;
import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;
import fspm.util.exceptions.KeyNotFoundException;

import static fspm.config.ConfigTestSuite.*;

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
                .getBoolean("useStaticArc"));
        println(CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("Boolean_variables")
                .getBoolean("inputLeafN"));

        println(CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("simulation_location")
                .getString("location_name"));

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

        println(store.getBoolean("useStaticArc"));
        println(store.getBoolean("inputLeafN"));

        store.setCategoryContext("simulation_location");

        println(store.getString("location_name"));

        // Access via aliasing

        println(" ===== Aliasing ");

        ParamCategory booleans = CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("Boolean_variables");

        println(booleans.getBoolean("useStaticArc"));
        println(booleans.getBoolean("inputLeafN"));

        ParamCategory simulationLocation = CONFIG
                .getGroup("model.input.data.name",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore().getCategory("simulation_location");

        println(simulationLocation.getString("location_name"));
    }

    @Test
    // @Ignore
    public void testNull() {
        CategoryStore store = CONFIG
                .getGroup("model.input.data.default",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(null, store.getDouble("BIOMASS_BERRY"));
        Assert.assertEquals(null, store.getString("BIOMASS_WOOD"));
    }

    @Test
    // @Ignore
    public void testNullWithDefaultValue() {
        CategoryStore store = CONFIG
                .getGroup("model.input.data.default",
                        DocumentCategoryNameGroup.class)
                .getCategoryStore()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(1, store.getDouble("BIOMASS_BERRY", 1), 0);
    }

    @Test
    // @Ignore
    public void testTypes() {
        CategoryStore store = CONFIG
                .getGroup("group", DocumentCategoryNameGroup.class)
                .getCategoryStore().setCategoryContext("category");

        println(store.getDouble("doubleParam"));
        println(store.getDouble("floatParam"));

        println(store.getDouble("nullParam") == null);
        println(store.getString("nullParam") == null);
        println(store.getInteger("nullParam") == null);
        println(store.getDouble("nullParam") == null);

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

        println(store.getDouble("BIOMASS_LEAF") == null);
    }

    @Test
    // @Ignore
    public void testCrossCategoryAccess() {
        CategoryStore store = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class).getCategoryStore();

        store.setCategoryContext("module_configuration");

        Assert.assertEquals(null, store.getBoolean("special_scenario"));

        store.setCategoryContext("model_functionality");

        Assert.assertTrue(store.getBoolean("calcLightInterception"));

        try {
            Assert.assertEquals(0,
                    store.getInteger("trainingSystem").intValue());
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
        store.useFlattenedCategories = true;

        println(store.getBoolean("useStaticArc"));
        println(store.getDouble("FractionDiffuseLightDaily"));

        println(store.getInteger("radiationControl"));
        println(store.getDouble("REFTMP"));

        println(store.getString("rootArchitecture_file"));
    }
}
