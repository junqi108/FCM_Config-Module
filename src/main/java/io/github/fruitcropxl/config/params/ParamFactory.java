package io.github.fruitcropxl.config.params;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.UnexpectedException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * ParamFactory creates instances of {@link io.github.fruitcropxl.config.params.Parameter
 * Parameters} depending on the data type of the field being read.
 * 
 * @author Ou-An Chuang
 */
public class ParamFactory {

    /**
     * Special case where "NA" represents null in .txt property files.
     */
    public static final String NULL_STRING = "NA";

    /**
     * Returns a {@link Parameter} instance which can be used to retrieve the parsed value.
     * 
     * @param name The parameter name.
     * @param node The JsonNode containing the parameter value and data type.
     * @return {@link Parameter} instance with the parsed value.
     */
    public Parameter parseParameter(String name, JsonNode node) {
        JsonNodeFactory factory = new JsonNodeFactory(false);

        try {
            if (node.isNull() || node.toString().equals(NULL_STRING)) {
                return new Parameter(name, factory.nullNode());
            } else {
                return new Parameter(name, node);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            throw new RuntimeException(String.format(
                    "An error occurred while parsing parameter: %s.\n%s", name,
                    sw));
        }

        // throw new UnsupportedOperationException(name + " uses an unsupported type.");
    }

    /**
     * Generic method for creating a new {@link Parameter} instance from a Java data type.
     * 
     * @param <T>
     * @param name
     * @param value Parameter value (must be a Java data type, e.g: int, String, double[]).
     * @return {@link Parameter} instance
     */
    public <T> Parameter createParameter(String name, T value) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.convertValue(value, JsonNode.class);
            return new Parameter(name, node);
        } catch (UnexpectedException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
