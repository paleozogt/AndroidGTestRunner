#include <string>

#include "gtest/gtest.h"

class GTestRunner {
public:
    GTestRunner(const std::string &tempDir) : tempDir(tempDir) {
    }

    virtual ~GTestRunner() {
    }

    bool run(const std::string &gtestArg, std::string &output) {
        captureStdout();

        const char *argv[]= { "./runner", gtestArg.c_str() };
        int argc= sizeof(argv)/sizeof(argv[0]);
        initGoogleTest(&argc, const_cast<char **>(argv));

        int failed= RUN_ALL_TESTS();

        output= getCapturedStdout();

        return failed==1;
    }

protected:
    void captureStdout() {
        tempFile= tempDir + "/gtest_captured_stream.XXXXXX";
        captured_fd = mkstemp((char*)(tempFile.c_str()));
        fflush(NULL);
        dup2(captured_fd, STDOUT_FILENO);
        close(captured_fd);
    }

    std::string getCapturedStdout() {
        FILE* const file = testing::internal::posix::FOpen(tempFile.c_str(), "r");
        const std::string output = testing::internal::ReadEntireFile(file);
        testing::internal::posix::FClose(file);
        return output;
    }

    void initGoogleTest(int* argc, char** argv) {
        // trick gtest into thinking it hasn't run yet
        ::testing::GTEST_FLAG(list_tests)= false;
        ::std::vector<testing::internal::string>& gargvs= (::std::vector<testing::internal::string>&)testing::internal::GetArgvs();
        gargvs.clear();

        ::testing::InitGoogleTest(argc, argv);
    }

    std::string tempDir;
    std::string tempFile;
    int captured_fd;
};
