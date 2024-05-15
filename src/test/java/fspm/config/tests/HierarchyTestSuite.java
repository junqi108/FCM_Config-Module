package fspm.config.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fspm.config.tests.Hierarchy.GroupTypeTest;
import fspm.config.tests.Hierarchy.TableHierarchyTest;

@RunWith(Suite.class)
@SuiteClasses({
		GroupTypeTest.class, TableHierarchyTest.class })

public class HierarchyTestSuite {
}
