package fspm.config;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.util.Arrays;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamTable;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.group.DocumentHybridCategoryNameGroup;
import fspm.config.params.hierarchy.CategoryHierarchy;
import fspm.config.params.type.*;

public class ConfigTest {
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
		
		DocumentHybridCategoryNameGroup hybridGroup = CONFIG.getGroup("hybrid-format", DocumentHybridCategoryNameGroup.class);
		println(hybridGroup);

		// ParamTable soilPhysicalProperties = hybridGroup.getTableHierarchy().getTable("soilPhysicalProperties");
		// println(soilPhysicalProperties.getValue("layer_1", "layerThickness"));
	}
	
	@Test
	// @Ignore
	public void testAccessExamples() {
		// Full descriptive access of hierarchy
		println(" ===== Full descriptive access ");
		
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class));

		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategoryHierarchy().getCategory("Boolean_variables").getBoolean("useStaticArc"));
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategoryHierarchy().getCategory("Boolean_variables").getBoolean("inputLeafN"));
		
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategoryHierarchy().getCategory("simulation_location").getString("location_name"));
		
		
		
		/**
		 * Contextual access
		 * 
		 * - Localises the selected category context to a local ParamGroup variable
		 */

		println(" ===== Contextual access ");
		
		CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class)
			.getCategoryHierarchy().setCategoryContext("Boolean_variables");
		
		println(hierarchy.getBoolean("useStaticArc"));
		println(hierarchy.getBoolean("inputLeafN"));
		
		hierarchy.setCategoryContext("simulation_location");
		
		println(hierarchy.getString("location_name"));

		
		
		// Access via aliasing
		
		println(" ===== Aliasing ");
		
		ParamCategory booleans = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategoryHierarchy().getCategory("Boolean_variables");
		
		println(booleans.getBoolean("useStaticArc"));
		println(booleans.getBoolean("inputLeafN"));
		
		ParamCategory simulationLocation = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategoryHierarchy().getCategory("simulation_location");
		
		println(simulationLocation.getString("location_name"));
	}
	
	@Test
	// @Ignore
	public void testTypes() {
		CategoryHierarchy hierarchy = CONFIG.getGroup("group", DocumentCategoryNameGroup.class).getCategoryHierarchy().setCategoryContext("category");
		
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
		println(CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class));
		
		CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class).getCategoryHierarchy().setCategoryContext("initial_condition_biomass");
		
		Double d = hierarchy.getDouble("BIOMASS_LEAF");

		println(hierarchy.getDouble("BIOMASS_LEAF") == null);
	}
	
	@Test
	// @Ignore
	public void testPhenology() {
		CategoryHierarchy hierarchy = CONFIG.getGroup("phenology.parameters.SauvignonBlanc", DocumentCategoryNameGroup.class).getCategoryHierarchy().setCategoryContext("parameters");
		
		println(Arrays.toString(hierarchy.getDoubleArray("BUDBURST_CANE_DIFF")));
	}
	
	@Test
	// @Ignore
	public void testFlatCategories() {
		CategoryHierarchy hierarchy = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class).getCategoryHierarchy();
		hierarchy.useFlattenedCategories = true;

		println(hierarchy.getBoolean("useStaticArc"));
		println(hierarchy.getDouble("FractionDiffuseLightDaily"));

		println(hierarchy.getDouble("radiationControl"));
		println(hierarchy.getDouble("REFTMP"));
		
		println(hierarchy.getString("rootArchitecture_file"));
	}
	
	@Test
	// @Ignore
	public void testArrays() {
		CategoryHierarchy hierarchy = CONFIG.getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class).getCategoryHierarchy();
		hierarchy.useFlattenedCategories = true;
		
		println(hierarchy.getIntegerArray("layerThickness")[0]);
		println(hierarchy.getIntegerArray("layerThickness").length);
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
