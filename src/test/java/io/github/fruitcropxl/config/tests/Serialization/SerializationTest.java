package io.github.fruitcropxl.config.tests.Serialization;

import static io.github.fruitcropxl.config.ConfigTestSuite.*;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import io.github.fruitcropxl.config.Config;
import io.github.fruitcropxl.config.params.ParamCategory;
import io.github.fruitcropxl.config.params.groups.DocumentCategoryNameGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// https://www.baeldung.com/java-validate-serializable
public class SerializationTest {
    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    @Test
    public void testConfig() {
        try {
            File file = serialize(CONFIG);
            deserialize(file, Config.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGroup() {
        try {
            File file = serialize(
                    CONFIG.getGroup("group", DocumentCategoryNameGroup.class));

            deserialize(file, DocumentCategoryNameGroup.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCategory() {
        try {
            File file = serialize(
                    CONFIG.getGroup("group", DocumentCategoryNameGroup.class)
                            .getCategoryStore().getCategory("category"));

            deserialize(file, ParamCategory.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testParameter() {
        try {
            File file = serialize(
                    CONFIG.getGroup("group", DocumentCategoryNameGroup.class)
                            .getCategoryStore().getCategory("category")
                            .get("doubleParam", Double.class));
            deserialize(file, Double.class);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private <T> File serialize(Object o) throws IOException {
        final String outFilePath = "serialized.txt";
        File outFile = new File(outFilePath);

        FileOutputStream fileOutputStream = new FileOutputStream(outFile,
                false);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                fileOutputStream)) {
            objectOutputStream.writeObject(o);
        }
        return outFile;
    }

    private <T> boolean deserialize(File file, Class<T> clazz)
            throws ClassNotFoundException, IOException {
        return deserialize(file, clazz, true);
    }

    private <T> boolean deserialize(File file, Class<T> clazz,
            boolean deleteFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);

        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                fileInputStream)) {
            T serializedConfig = clazz.cast(objectInputStream.readObject());

            println(serializedConfig);

            // TODO: tests to determine if return false
        }

        if (deleteFile) {
            file.delete();
        }
        return true;
    }
}
