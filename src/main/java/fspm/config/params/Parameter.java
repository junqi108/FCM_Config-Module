package fspm.config.params;

import java.rmi.UnexpectedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import fspm.util.KeyElement;
import fspm.util.exceptions.KeyNotFoundException;
import fspm.util.exceptions.TypeNotFoundException;

/**
 * Parameter represents variables that can be stored using Java data types
 * or other user-defined types.
 * <p>
 * The value field should be implemented by extending members, as values require
 * specific primitive type declarations.
 * 
 * @author Ou-An Chuang
 */
public class Parameter extends KeyElement {

    private JsonNode node;

    /**
     * @param key The parameter key.
     * @throws UnexpectedException
     */
    public Parameter(String key, JsonNode node) throws UnexpectedException {
        super(key);

        if (node.isObject()) {
            throw new UnexpectedException("Cannot store an object as a parameter.");
        } else {
            this.node = node;
        }
    }

    // @SuppressWarnings("unchecked")
    // public <T> T getValue() {
    // System.out.println(node.getNodeType());
    // try {
    // if (node.isTextual()) {
    // return (T) node.asText();
    // } else if (node.isDouble()) {
    // return (T) Double.valueOf(node.asDouble());
    // } else if (node.isInt()) {
    // return (T) Integer.valueOf(node.asInt());
    // } else if (node.isBoolean()) {
    // return (T) Boolean.valueOf(node.asBoolean());
    // } else if (node.isArray()) {
    // ObjectMapper objectMapper = new ObjectMapper();
    // return (T) objectMapper.treeToValue(node, ArrayList.class);
    // }
    // } catch (Exception e) {
    // StringWriter sw = new StringWriter();
    // e.printStackTrace(new PrintWriter(sw));
    // throw new RuntimeException(
    // String.format("An error occurred while parsing parameter: %s.\n%s",
    // super.getKey(), sw));
    // }
    // throw new UnsupportedOperationException(super.getKey() + " uses an
    // unsupported type.");
    // }

    public JsonNodeType getType() {
        return node.getNodeType();
    }

    public Boolean asBoolean() {
        if (isNull()) {
            return null;
        }
        if (node.isBoolean()) {
            return node.asBoolean();
        }
        throw new KeyNotFoundException(super.getKey());
    }

    public String asString() {
        if (isNull()) {
            return null;
        }
        if (node.isTextual()) {
            return node.asText();
        }
        throw new KeyNotFoundException(super.getKey());
    }

    public Integer asInteger() {
        if (isNull()) {
            return null;
        }
        if (node.isInt()) {
            return node.asInt();
        }
        throw new KeyNotFoundException(super.getKey());
    }

    public Double asDouble() {
        if (isNull()) {
            return null;
        }
        if (node.isDouble()) {
            return node.asDouble();
        } else if (node.isInt()) {
            // Case where double parameters are written as integers, e.g: "1.0" becomes "1"
            return (double) node.asInt();
        } else if (node.isTextual()) {
            // Case where floats are stored as strings, e.g: "10.5f"
            String value = node.asText();
            return (double) Float.parseFloat(value);
        }
        throw new KeyNotFoundException(super.getKey());
    }

    public Boolean[] asBooleanArray() {
        return asArray(Boolean[].class);
    }

    public String[] asStringArray() {
        return asArray(String[].class);
    }

    public Integer[] asIntegerArray() {
        return asArray(Integer[].class);
    }

    public Double[] asDoubleArray() {
        return asArray(Double[].class);
    }

    public <T> T[] asArray(Class<T[]> type) {
        if (isNull()) {
            return null;
        }
        if (type == null) {
            throw new RuntimeException("Array type should not be null");
        }
        if (node.isArray()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode firstItem = node.get(0);

                System.out.println(firstItem.getNodeType());

                if (firstItem.isInt() && type.equals(Integer[].class)
                        || firstItem.isDouble() && type.equals(Double[].class)
                        || firstItem.isTextual() && type.equals(String[].class)
                        || firstItem.isBoolean() && type.equals(Boolean[].class)) {
                    return objectMapper.treeToValue(node, type);
                } else {
                    throw new TypeNotFoundException(getKey(), type.getSimpleName());
                }
            } catch (JsonProcessingException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        throw new KeyNotFoundException(super.getKey());
    }

    public boolean isNull() {
        return node.isNull();
    }

    @Override
    public String toString() {
        return super.getKey() + ": " + node.asText();
    }
}
