package fspm.config.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fspm.config.tests.ParamAccess.ArrayTest;
import fspm.config.tests.ParamAccess.SimpleTest;

@RunWith(Suite.class)
@SuiteClasses({ ArrayTest.class, SimpleTest.class })

public class ParamAccessTestSuite {
}
