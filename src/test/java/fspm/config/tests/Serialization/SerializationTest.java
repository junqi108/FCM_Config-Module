package fspm.config.tests.Serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fspm.config.Config;
import fspm.config.params.ParamCategory;
import fspm.config.params.groups.DocumentCategoryNameGroup;

import static fspm.config.ConfigTestSuite.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;

// https://www.baeldung.com/java-validate-serializable
public class SerializationTest {
    @Before
    public void reset() {
        CONFIG.reset();
        addGroups(CONFIG);
    }

    // @Test
    // public void testCategory() {
    // assertTrue(isSerializable(
    // CONFIG.getGroup("group", DocumentCategoryNameGroup.class)
    // .getCategoryStore().getCategory("category"),
    // ParamCategory.class));
    // }

    @Test
    public void testParameter() {
        assertTrue(isSerializable(
                CONFIG.getGroup("group", DocumentCategoryNameGroup.class)
                        .getCategoryStore().getCategory("category")
                        .getDouble("doubleParam"),
                Double.class));
    }

    private <T> boolean isSerializable(Object o, Class<T> clazz) {
        final String outFilePath = "serialized.txt";
        File outFile = new File(outFilePath);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outFile,
                    false);

            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    fileOutputStream)) {
                objectOutputStream.writeObject(o);
            } catch (NotSerializableException e) {
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(outFile);

            try (ObjectInputStream objectInputStream = new ObjectInputStream(
                    fileInputStream)) {
                T serializedConfig = clazz.cast(objectInputStream.readObject());

                println(serializedConfig);

                // TODO: tests to determine if return false
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            // outFile.delete();
        }
    }
}
