plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    neoFormVersion = "26.2-2"

    withGametest()
}