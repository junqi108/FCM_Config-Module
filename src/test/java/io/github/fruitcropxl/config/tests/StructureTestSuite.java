package io.github.fruitcropxl.config.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.github.fruitcropxl.config.tests.Structure.GroupTypeTest;
import io.github.fruitcropxl.config.tests.Structure.TableStoreTest;

@RunWith(Suite.class)
@SuiteClasses({ GroupTypeTest.class, TableStoreTest.class })

public class StructureTestSuite {
}
