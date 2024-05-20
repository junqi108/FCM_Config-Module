package fspm.config.tests;

import java.io.FileNotFoundException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fspm.config.Config;
import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamFactory;
import fspm.config.params.groups.DocumentCategoryNameGroup;
import fspm.config.params.structures.CategoryStore;
import fspm.config.tests.ParamAccess.ArrayTest;
import fspm.config.tests.ParamAccess.SimpleTest;

@RunWith(Suite.class)
@SuiteClasses({ ArrayTest.class, SimpleTest.class })

public class ParamAccessTestSuite {
    public static void addGroups(Config CONFIG) {
        // Manually add new group
        ParamFactory factory = new ParamFactory();

        ParamCategory category = new ParamCategory("category");
        category.addParameter(factory.createParameter("doubleParam", 1.5));
        category.addParameter(factory.createParameter("floatParam", "1.0f"));
        category.addParameter(factory.createParameter("nullParam", null));

        String groupKey = "group";

        CategoryStore categoryStore = new CategoryStore(groupKey);
        categoryStore.addCategory(category);

        DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(
                groupKey, categoryStore);

        CONFIG.addGroup(group);

        // Read in JSON file and add as new group
        try {
            CONFIG.addGroup("model.input.data.name", new JsonFileReader(
                    "./inputs/parameters/model.input.data.name.json"));
            CONFIG.addGroup("model.input.data.default", new JsonFileReader(
                    "./inputs/parameters/model.input.data.default.json"));
            CONFIG.addGroup("phenology.parameters.SauvignonBlanc",
                    new JsonFileReader(
                            "./inputs/parameters/phenology.parameters.SauvignonBlanc.json"));

            CONFIG.addGroup("paramSetTest", new JsonFileReader(
                    "./inputs/parameters/paramSetTest.json"));

            CONFIG.addGroup("soilParams_pot_1", new JsonFileReader(
                    "./inputs/parameters/soilParams_pot_1.json"));

            CONFIG.addGroup("hybrid-format", new JsonFileReader(
                    "./inputs/parameters/hybrid-format.json"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
