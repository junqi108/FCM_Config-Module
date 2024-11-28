package io.github.fruitcropxl.config.params.groups;

import io.github.fruitcropxl.config.util.KeyElement;

/**
 * Parameter configuration class for storing parameter categories. Implementing classes should
 * specify their format in their names and be composed of
 * {@link io.github.fruitcropxl.config.params.structures.ParamStructure}.
 * 
 * @author Ou-An Chuang
 */
public abstract class ParamGroup extends KeyElement {
    /**
     * @param key Name of the document/file, e.g.: 'model.input.data.default'
     */
    protected ParamGroup(String key) {
        super(key);
    }
}
