package fspm.config.params;

/**
 * Blueprint for all classes that handles getting and setting of parameters.
 * FIXME: currently unused
 */
public interface ParamAccessor {
	Boolean getBoolean(String key);

	String getString(String key);

	Integer getInteger(String key);

	Double getDouble(String key);

	// Integer[] getIntegerArray(String key);

	// Double[] getDoubleArray(String key);
	public <T> T[] getArray(String key, Class<T[]> type);

	void set(String key, boolean value);

	void set(String key, String value);

	void set(String key, int value);

	void set(String key, double value);

	void set(String key, Double[] value);

	boolean isNull(String key);
}
