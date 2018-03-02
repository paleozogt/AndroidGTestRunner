package org.paleozogt.gtestrunner;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.apache.commons.lang3.mutable.MutableObject;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class GTestRunnerTests {
    public String TAG= getClass().getSimpleName();

    @Parameterized.Parameters(name = "{0}")
    public static List<String> getTestList() {
        GTestRunner gtestRunner= new GTestRunner(InstrumentationRegistry.getTargetContext());
        return gtestRunner.getTests();
    }

    protected String testName;
    protected GTestRunner gtestRunner;

    public GTestRunnerTests(String testName) {
        this.testName= testName;
        gtestRunner= new GTestRunner(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testGTest() {
        MutableObject<String> output= new MutableObject<>();
        boolean failed= gtestRunner.run("--gtest_filter=" + testName, output);
        Log.d(TAG, output.toString());
        assertFalse(failed);
    }
}
