package io.github.fruitcropxl.config.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.github.fruitcropxl.config.tests.ParamAccess.ArrayTest;
import io.github.fruitcropxl.config.tests.ParamAccess.SimpleTest;

@RunWith(Suite.class)
@SuiteClasses({ ArrayTest.class, SimpleTest.class })

public class ParamAccessTestSuite {
}
