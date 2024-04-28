package fspm.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.util.Arrays;

import fspm.config.Config;
import fspm.config.adapters.JsonFileReader;
import fspm.config.params.ParamCategory;
import fspm.config.params.ParamGroup;
import fspm.config.params.type.*;

public class ConfigTest {
	static final Config CONFIG = Config.getInstance();
	
	@Before
	public void reset() {
		CONFIG.reset();
		addGroups();
	}
	
	
	
	
	@Test
	@Ignore
	public void testAccessExamples() {
		// Full descriptive access of hierarchy
//		println(" ===== Full descriptive access ");
		
		println(CONFIG.getGroup("model.input.data.name"));
//		
//		println(CONFIG.getGroup("model.input.data.name").getCategory("Boolean_variables").getBoolean("useStaticArc"));
//		println(CONFIG.getGroup("model.input.data.name").getCategory("Boolean_variables").getBoolean("inputLeafN"));
//		
//		println(CONFIG.getGroup("model.input.data.name").getCategory("simulation_location").getString("location_name"));
		
		
		
		// Contextual access

		println(" ===== Contextual access ");
		
		CONFIG.setGroupContext("model.input.data.name")
			.setCategoryContext("Boolean_variables");
		
		println(CONFIG.getBoolean("useStaticArc"));
		println(CONFIG.getBoolean("inputLeafN"));
		
		CONFIG.setCategoryContext("simulation_location");
		
		println(CONFIG.getString("location_name"));

		
		
		// Access via aliasing
		
//		println(" ===== Aliasing ");
//		
//		ParamCategory booleans = CONFIG.getGroup("model.input.data.name").getCategory("Boolean_variables");
//		
//		println(booleans.getBoolean("useStaticArc"));
//		println(booleans.getBoolean("inputLeafN"));
//		
//		ParamCategory simulationLocation = CONFIG.getGroup("model.input.data.name").getCategory("simulation_location");
//		
//		println(simulationLocation.getString("location_name"));
		
		
		
		// Direct access; not compatible with contextual access
		
//		println(" ===== Direct access");
//		println(CONFIG.getBoolean("useStaticArc"));
	}
	
	@Test
	@Ignore
	public void testTypes() {
		CONFIG.setGroupContext("group");
		CONFIG.setCategoryContext("category");
		
		println(CONFIG.getDouble("doubleParam"));
		println(CONFIG.getDouble("floatParam"));
		
		println(CONFIG.getDouble("nullParam") == null);
		println(CONFIG.getString("nullParam") == null);
		println(CONFIG.getInteger("nullParam") == null);
		println(CONFIG.getDouble("nullParam") == null);
		
		println(CONFIG.isNull("nullParam"));
	}
	
	@Test
	@Ignore
	public void testDefault() {
		println(CONFIG.getGroup("model.input.data.default"));
		
		CONFIG.setGroupContext("model.input.data.default");
		CONFIG.setCategoryContext("initial_condition_biomass");
		
		Double d = CONFIG.getDouble("BIOMASS_LEAF");

		println(CONFIG.getDouble("BIOMASS_LEAF") == null);
	}
	
	@Test
	@Ignore
	public void testPhenology() {
		CONFIG.setGroupContext("phenology.parameters.SauvignonBlanc");
		CONFIG.setCategoryContext("parameters");
		
		println(Arrays.toString(CONFIG.getDoubleArray("BUDBURST_CANE_DIFF")));
	}
	
	@Test
	@Ignore
	public void testFlatCategories() {
		CONFIG.useFlattenedCategories = true;
		
		String group = "model.input.data.default";
		
		CONFIG.setGroupContext(group);
		
		
		println(CONFIG.getGroup(group));
		
		println(CONFIG.getBoolean("useStaticArc"));
		println(CONFIG.getDouble("FractionDiffuseLightDaily"));

		println(CONFIG.getDouble("radiationControl"));
		println(CONFIG.getDouble("REFTMP"));
		
		println(CONFIG.getString("rootArchitecture_file"));
	}
	
	@Test
//	@Ignore
	public void testArrays() {
		CONFIG.setGroupContext("soilParams_pot_1");
		CONFIG.useFlattenedCategories = true;
		
		println(CONFIG.getIntegerArray("layerThickness"));
	}
	
	
	
	
	
	
	
	
	
	
	
	private static void addGroups() {
		// Manually add new group
		ParamCategory category = new ParamCategory("category");
		category.add(new IntegerParam("doubleParam", 1));
		category.add(new StringParam("floatParam", "1.0f"));
		category.add(new NullParam("nullParam"));
		
		ParamGroup group = new ParamGroup("group");
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void println(Object o) {
		System.out.println(o.toString());
	}

}
