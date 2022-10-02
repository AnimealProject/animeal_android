plugins {
    id("animeal.library")
}

animealPlugin {
    compose = false
    buildConfigGeneration = false
}

android {
    namespace = "com.epmedu.animeal.resources"
}
