package fspm.config.params;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.UnexpectedException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * ParamFactory creates instances of concrete {@link Parameter Parameter} implementations depending
 * on the data type received.
 * 
 * @author Ou-An Chuang
 */
public class ParamFactory {

    public static final String NULL_STRING = "NA";

    /**
     * Returns a concrete {@link Parameter Parameter} instance depending on the data type of the
     * JsonNode passed in.
     * 
     * @param name The parameter name.
     * @param node The JsonNode containing the parameter value and data type.
     * @return Concrete {@link Parameter Parameter} instance of the corresponding data type.
     * @throws UnsupportedOperationException If the JsonNode data type does not correspond to any
     *                                       concrete {@link Parameter} implementation.
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

    public <T> Parameter createParameter(String name, T value) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(value, JsonNode.class);
        try {
            return new Parameter(name, node);
        } catch (UnexpectedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
