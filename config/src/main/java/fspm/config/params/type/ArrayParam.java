package fspm.config.params.type;

import fspm.config.params.Parameter;

public class ArrayParam<T> extends Parameter {
	private T[] value;
	
	public ArrayParam(String key, T[] value) {
		super(key);
		
		this.value = value;
	}
	
	public T[] getValue() {
		return this.value;
	}
	
	public void setValue(T[] value) {
		this.value = value;
	}
	
	@Override
    public String toString() {
        return super.getKey() + ": " + value;
    }
}
