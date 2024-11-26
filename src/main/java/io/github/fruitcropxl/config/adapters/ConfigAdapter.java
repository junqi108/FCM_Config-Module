package io.github.fruitcropxl.config.adapters;

import java.io.FileNotFoundException;

import io.github.fruitcropxl.config.params.groups.ParamGroup;

/**
 * ConfigAdapter implementations are responsible for parsing different file types into ParamGroups.
 * 
 * @author Ou-An Chuang
 */
public abstract class ConfigAdapter {
    protected final String path;

    /**
     * Specify the file path to be read here. Each adapter instance is responsible for reading only one
     * file, similar to {@link java.io.FileReader}.
     * 
     * @param filePath File path to parameter configuration.
     */
    protected ConfigAdapter(String path) {
        this.path = path;
    }

    /**
     * Parses and returns a {@link ParamGroup} with the parameter contents of the provided file.
     * 
     * @return Parsed {@link ParamGroup} for {@link io.github.fruitcropxl.config.Config}.
     */
    public abstract ParamGroup parse() throws FileNotFoundException;
}
