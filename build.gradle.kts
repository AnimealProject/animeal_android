import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt") version libs.versions.detekt
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.plugin.buildtools)
        classpath(libs.gradle.plugin.hilt)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.googleservices)
        classpath(libs.gradle.plugin.crashlytics)
    }
}

detekt {
    config = files("config/detekt/detekt.yml")
    allRules = true
    autoCorrect = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    detekt {
        autoCorrect = true
    }
}

allprojects {
    dependencies {
        detektPlugins(rootProject.project.libs.gradle.plugin.detekt.formatting)
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}