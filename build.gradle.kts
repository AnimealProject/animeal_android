import com.epmedu.animeal.extension.propertyInt
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.konan.file.File
import org.jetbrains.kotlin.konan.properties.loadProperties
import org.jetbrains.kotlin.konan.properties.saveToFile

apply(plugin = "com.github.ben-manes.versions")

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
        classpath(libs.gradle.plugin.crashlytics)
        classpath(libs.gradle.plugin.googleservices)
        classpath(libs.gradle.plugin.hilt)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.versions)
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

    register("incrementVersion") {
        val properties = loadProperties("$rootDir/app/version.properties")

        val newVersion = properties.propertyInt("BUILD_VERSION").inc()

        if (newVersion == 1000) {
            val subVersion = properties.propertyInt("SUB_VERSION").inc()

            properties["SUB_VERSION"] = subVersion.toString()
            properties["BUILD_VERSION"] = 0.toString()
        } else {
            properties["BUILD_VERSION"] = newVersion.toString()
        }

        properties.saveToFile(File("$rootDir/app/version.properties"))
    }
}