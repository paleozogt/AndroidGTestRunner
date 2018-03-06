# Android GTest Runner

Do you have [GTest](https://github.com/google/googletest) tests that you'd like to run on Android?  Typically you create a CLI test runner, deploy to a phone with adb, and run the tests.

But what about running the tests in an APK via JNI?  What about all the JUnit infrastructure in Android Studio and online tools like TestDroid?

This project shows how to do it.

First, you need source code.  I recommend making a branch of this project and submoduling in your project.  Wire it up to the project with `app/CMakeLists.txt`.

Next you need tests.  If you have these in your submoduled project, you're probably compiling them into a test runner CLI.  In `app/CMakeLists.txt`, compile these same sources into a native lib (`libGTestRunner`).

This project shows an example set of tests, `app/src/main/cpp/tests.cpp` (one of which is supposed to fail):
```
TEST(foo, bar) {
    ASSERT_TRUE(true);
}

TEST(foo, baz) {
    ASSERT_TRUE(false);
}
```

Since the app has JUnit integration with the GTests, running the tests on-device in Android Studio looks like:

![gtestrunner-androidstudio](https://user-images.githubusercontent.com/310894/37060163-ba141146-214c-11e8-9a44-de8df5722b2c.png)

while running from the command-line looks like:
```
➜  GTestRunner git:(master) ✗ ./gradlew connectedCheck

> Task :app:externalNativeBuildDebug
...

> Task :app:connectedDebugAndroidTest
Starting 2 tests on XT1097 - 5.1

org.paleozogt.gtestrunner.GTestRunnerTests > testGTest[foo.baz][XT1097 - 5.1] FAILED 
        java.lang.AssertionError
        at org.junit.Assert.fail(Assert.java:86)


FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:connectedDebugAndroidTest'.
> There were failing tests. See the report at: file:///Users/paleozogt/GTestRunner/app/build/reports/androidTests/connected/index.html

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output.

* Get more help at https://help.gradle.org

BUILD FAILED in 16s
52 actionable tasks: 4 executed, 48 up-to-date
```

Additionally, the app has a gui for running individual tests:

![listing](https://user-images.githubusercontent.com/310894/37060997-3587854a-214f-11e8-90ca-8aed09192f62.png) ![testrun](https://user-images.githubusercontent.com/310894/37061361-7f5b96e2-2150-11e8-8886-e03f0c8ee12c.png)
