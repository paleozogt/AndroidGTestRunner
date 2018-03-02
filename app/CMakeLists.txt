cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(src/main/cpp/gtest)

add_library(GTestRunner SHARED src/main/cpp/GTestRunnerJNI.cpp src/main/cpp/tests.cpp)
target_link_libraries(GTestRunner log gtest)
