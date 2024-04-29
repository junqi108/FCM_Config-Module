package fspm.config.adapters.handlers;

import java.io.FileNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import fspm.config.params.ParamGroup;

public abstract class MetaclassHandler {
	public abstract ParamGroup parse(String path) throws FileNotFoundException;
}
