cmake_minimum_required(VERSION 3.25)
project(native)

set(CMAKE_CXX_STANDARD 20)

include_directories(/fakepath/graalvm-17/include)
include_directories(/fakepath/graalvm-17/include/linux)
include_directories(include)

add_library(native SHARED FontConfig.cpp)

target_link_libraries(native fontconfig)
