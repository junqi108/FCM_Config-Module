package fspm.config.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.util.Arrays;

import fspm.config.Config;
import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamTable;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.group.DocumentHybridCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;
import fspm.config.params.type.*;

public class DocumentReadingTest {
	static final Config CONFIG = Config.getInstance();

	@Before
	public void reset() {
		CONFIG.reset();
		addGroups();
	}

	@Test
	// @Ignore
	public void testGroupTypes() {
		DocumentCategoryNameGroup group = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class);
		println(group);

		DocumentHybridCategoryNameGroup hybridGroup = CONFIG.getGroup("hybrid-format",
				DocumentHybridCategoryNameGroup.class);
		println(hybridGroup);

		// ParamTable soilPhysicalProperties =
		// hybridGroup.getTableHierarchy().getTable("soilPhysicalProperties");
		// println(soilPhysicalProperties.getValue("layer_1", "layerThickness"));
	}

	private static void addGroups() {
		// Manually add new group
		ParamCategory category = new ParamCategory("category");
		category.add(new IntegerParam("doubleParam", 1));
		category.add(new StringParam("floatParam", "1.0f"));
		category.add(new NullParam("nullParam"));

		String groupKey = "group";

		CategoryHierarchy categoryHierarchy = new CategoryHierarchy(groupKey);
		categoryHierarchy.addCategory(category);

		DocumentCategoryNameGroup group = new DocumentCategoryNameGroup(groupKey, categoryHierarchy);

		CONFIG.addGroup(group);

		// Read in JSON file and add as new group
		try {
			CONFIG.addGroup("model.input.data.name",
					new JsonFileReader("./inputs/parameters/model.input.data.name.json"));
			CONFIG.addGroup("model.input.data.default",
					new JsonFileReader("./inputs/parameters/model.input.data.default.json"));
			CONFIG.addGroup("phenology.parameters.SauvignonBlanc",
					new JsonFileReader("./inputs/parameters/phenology.parameters.SauvignonBlanc.json"));

			CONFIG.addGroup("soilParams_pot_1",
					new JsonFileReader("./inputs/parameters/soilParams_pot_1.json"));

			CONFIG.addGroup("hybrid-format",
					new JsonFileReader("./inputs/parameters/hybrid-format.json"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void println(Object o) {
		System.out.println(o.toString());
	}

}
