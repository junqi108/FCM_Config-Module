package fspm.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fspm.config.tests.DocumentReadingTest;
import fspm.config.tests.ParamAccessTest;

@RunWith(Suite.class)
@SuiteClasses({
                DocumentReadingTest.class, ParamAccessTest.class })

public class ConfigTestSuite {
}
