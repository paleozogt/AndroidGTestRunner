#include "gtest/gtest.h"
using namespace ::testing;


TEST(foo, bar) {
    ASSERT_TRUE(true);
}

TEST(foo, baz) {
    ASSERT_TRUE(false);
}
