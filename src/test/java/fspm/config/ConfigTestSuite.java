package fspm.config;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fspm.config.tests.HierarchyTestSuite;
import fspm.config.tests.ParamAccessTestSuite;
import fspm.config.tests.ParamAccess.ArrayTest;

@RunWith(Suite.class)
@SuiteClasses({
        ParamAccessTestSuite.class })

public class ConfigTestSuite {
    public static final Config CONFIG = Config.getInstance();

    public static void println(Object o) {
        System.out.println(o.toString());
    }
}
