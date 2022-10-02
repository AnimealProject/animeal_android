plugins {
    `kotlin-dsl`
}

group = "com.epmedu.animeal"

dependencies {
    compileOnly(libs.gradle.plugin.buildtools)
    compileOnly(libs.gradle.plugin.kotlin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("animealLibrary") {
            id = "animeal.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("animealApplication") {
            id = "animeal.application"
            implementationClass = "AndroidApplicationPlugin"
        }
    }
}