package fspm.config.tests.ParamAccess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fspm.config.params.ParamCategory;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;
import fspm.config.tests.ParamAccessTestSuite;
import fspm.util.exceptions.KeyNotFoundException;

import static fspm.config.ConfigTestSuite.*;

public class SimpleTest {

    @Before
    public void reset() {
        CONFIG.reset();
        ParamAccessTestSuite.addGroups(CONFIG);
    }

    @Test
    // @Ignore
    public void testAccessExamples() {
        // Full descriptive access of hierarchy
        println(" ===== Full descriptive access ");

        println(CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class));

        println(CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .getCategory("Boolean_variables").getBoolean("useStaticArc"));
        println(CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .getCategory("Boolean_variables").getBoolean("inputLeafN"));

        println(CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .getCategory("simulation_location").getString("location_name"));

        /**
         * Contextual access
         *
         * - Localises the selected category context to a local ParamGroup variable
         */

        println(" ===== Contextual access ");

        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy().setCategoryContext("Boolean_variables");

        println(hierarchy.getBoolean("useStaticArc"));
        println(hierarchy.getBoolean("inputLeafN"));

        hierarchy.setCategoryContext("simulation_location");

        println(hierarchy.getString("location_name"));

        // Access via aliasing

        println(" ===== Aliasing ");

        ParamCategory booleans = CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy().getCategory("Boolean_variables");

        println(booleans.getBoolean("useStaticArc"));
        println(booleans.getBoolean("inputLeafN"));

        ParamCategory simulationLocation = CONFIG.getGroup("model.input.data.name",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy().getCategory("simulation_location");

        println(simulationLocation.getString("location_name"));
    }

    @Test
    // @Ignore
    public void testNull() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(null, hierarchy.getDouble("BIOMASS_BERRY"));
        Assert.assertEquals(null, hierarchy.getString("BIOMASS_WOOD"));
    }

    @Test
    // @Ignore
    public void testNullWithDefaultValue() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .setCategoryContext("initial_condition_biomass");

        Assert.assertEquals(1, hierarchy.getDouble("BIOMASS_BERRY", 1), 0);
    }

    @Test
    // @Ignore
    public void testTypes() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("group",
                DocumentCategoryNameGroup.class).getCategoryHierarchy()
                .setCategoryContext("category");

        println(hierarchy.getDouble("doubleParam"));
        println(hierarchy.getDouble("floatParam"));

        println(hierarchy.getDouble("nullParam") == null);
        println(hierarchy.getString("nullParam") == null);
        println(hierarchy.getInteger("nullParam") == null);
        println(hierarchy.getDouble("nullParam") == null);

        println(hierarchy.isNull("nullParam"));
    }

    @Test
    // @Ignore
    public void testDefault() {
        println(CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class));

        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default",
                DocumentCategoryNameGroup.class)
                .getCategoryHierarchy().setCategoryContext("initial_condition_biomass");

        println(hierarchy.getDouble("BIOMASS_LEAF") == null);
    }

    @Test
    // @Ignore
    public void testCrossCategoryAccess() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();

        hierarchy.setCategoryContext("module_configuration");

        Assert.assertEquals(null, hierarchy.getBoolean("special_scenario"));

        hierarchy.setCategoryContext("model_functionality");

        Assert.assertTrue(hierarchy.getBoolean("calcLightInterception"));

        try {
            Assert.assertEquals(0, hierarchy.getInteger("trainingSystem").intValue());
            Assert.fail(
                    "Should have thrown KeyNotFoundException as trainingSystem is not in the model_functionality category");
        } catch (KeyNotFoundException e) {
        }
    }

    @Test
    // @Ignore
    public void testFlatCategories() {
        CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class)
                .getCategoryHierarchy();
        hierarchy.useFlattenedCategories = true;

        println(hierarchy.getBoolean("useStaticArc"));
        println(hierarchy.getDouble("FractionDiffuseLightDaily"));

        println(hierarchy.getInteger("radiationControl"));
        println(hierarchy.getDouble("REFTMP"));

        println(hierarchy.getString("rootArchitecture_file"));
    }
}
