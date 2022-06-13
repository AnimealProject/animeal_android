plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

gradlePlugin {
    plugins {
        register("AnimalisPlugin") {
            id = "AnimalisPlugin"
            implementationClass = "com.epmedu.animeal.AnimalisPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    //https://github.com/google/dagger/issues/3068#issuecomment-999118496
    implementation(libs.javapoet)

    implementation(libs.gradle.plugin.buildtools)
    implementation(libs.gradle.plugin.kotlin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}