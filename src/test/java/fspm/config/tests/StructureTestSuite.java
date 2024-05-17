package fspm.config.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fspm.config.tests.Structure.GroupTypeTest;
import fspm.config.tests.Structure.TableStoreTest;

@RunWith(Suite.class)
@SuiteClasses({ GroupTypeTest.class, TableStoreTest.class })

public class StructureTestSuite {
}
