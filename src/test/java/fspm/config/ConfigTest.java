package fspm.config;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.util.Arrays;

import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.group.DocumentCategoryNameGroup;
import fspm.config.params.group.DocumentHybridCategoryNameGroup;
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
	}
	
	@Test
	// @Ignore
	public void testAccessExamples() {
		// Full descriptive access of hierarchy
		println(" ===== Full descriptive access ");
		
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class));

		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategory("Boolean_variables").getBoolean("useStaticArc"));
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategory("Boolean_variables").getBoolean("inputLeafN"));
		
		println(CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategory("simulation_location").getString("location_name"));
		
		
		
		/**
		 * Contextual access
		 * 
		 * - Localises the selected category context to a local ParamGroup variable
		 */

		println(" ===== Contextual access ");
		
		DocumentCategoryNameGroup group = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class)
			.getWithCategoryContext("Boolean_variables");
		
		println(group.getBoolean("useStaticArc"));
		println(group.getBoolean("inputLeafN"));
		
		group.getWithCategoryContext("simulation_location");
		
		println(group.getString("location_name"));

		
		
		// Access via aliasing
		
		println(" ===== Aliasing ");
		
		ParamCategory booleans = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategory("Boolean_variables");
		
		println(booleans.getBoolean("useStaticArc"));
		println(booleans.getBoolean("inputLeafN"));
		
		ParamCategory simulationLocation = CONFIG.getGroup("model.input.data.name", DocumentCategoryNameGroup.class).getCategory("simulation_location");
		
		println(simulationLocation.getString("location_name"));
	}
	
	@Test
	// @Ignore
	public void testTypes() {
		DocumentCategoryNameGroup group = CONFIG.getGroup("group", DocumentCategoryNameGroup.class).getWithCategoryContext("category");
		
		println(group.getDouble("doubleParam"));
		println(group.getDouble("floatParam"));
		
		println(group.getDouble("nullParam") == null);
		println(group.getString("nullParam") == null);
		println(group.getInteger("nullParam") == null);
		println(group.getDouble("nullParam") == null);
		
		println(group.isNull("nullParam"));
	}
	
	@Test
	// @Ignore
	public void testDefault() {
		println(CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class));
		
		DocumentCategoryNameGroup group = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class).getWithCategoryContext("initial_condition_biomass");
		
		Double d = group.getDouble("BIOMASS_LEAF");

		println(group.getDouble("BIOMASS_LEAF") == null);
	}
	
	@Test
	// @Ignore
	public void testPhenology() {
		DocumentCategoryNameGroup group = CONFIG.getGroup("phenology.parameters.SauvignonBlanc", DocumentCategoryNameGroup.class).getWithCategoryContext("parameters");
		
		println(Arrays.toString(group.getDoubleArray("BUDBURST_CANE_DIFF")));
	}
	
	@Test
	// @Ignore
	public void testFlatCategories() {
		DocumentCategoryNameGroup group = CONFIG.getGroup("model.input.data.default", DocumentCategoryNameGroup.class);

		println(group.getBoolean("useStaticArc"));
		println(group.getDouble("FractionDiffuseLightDaily"));

		println(group.getDouble("radiationControl"));
		println(group.getDouble("REFTMP"));
		
		println(group.getString("rootArchitecture_file"));
	}
	
	@Test
	// @Ignore
	public void testArrays() {
		DocumentCategoryNameGroup group = CONFIG.getGroup("soilParams_pot_1", DocumentCategoryNameGroup.class);
		
		println(group.getIntegerArray("layerThickness")[0]);
		println(group.getIntegerArray("layerThickness").length);
	}
	
	
	
	
	
	
	
	
	
	
	
	private static void addGroups() {
		// Manually add new group
		ParamCategory category = new ParamCategory("category");
		category.add(new IntegerParam("doubleParam", 1));
		category.add(new StringParam("floatParam", "1.0f"));
		category.add(new NullParam("nullParam"));
		
		DocumentCategoryNameGroup group = new DocumentCategoryNameGroup("group");
		group.addCategory(category);
		
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
