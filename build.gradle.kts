import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import java.util.Locale
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
        classpath(libs.gradle.plugin.appsync)
        classpath(libs.gradle.plugin.android)
        classpath(libs.gradle.plugin.crashlytics)
        classpath(libs.gradle.plugin.googleservices)
        classpath(libs.gradle.plugin.hilt)
        classpath(libs.gradle.plugin.kotlin)
        classpath(libs.gradle.plugin.versions)
    }
}

detekt {
    config.setFrom(files("config/detekt/detekt.yml"))
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
        detektPlugins(rootProject.project.libs.gradle.plugin.detekt.compose)
        detektPlugins(rootProject.project.libs.gradle.plugin.detekt.formatting)
    }
}

tasks {
    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }

    registering(Delete::class) {
        delete(layout.buildDirectory)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase(Locale.getDefault()).contains(it)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            // https://issuetracker.google.com/issues/285090974
            val args = mutableListOf("-Xstring-concat=inline")
            val buildDirectoryPath = project.layout.buildDirectory.get().asFile.absolutePath

            if (project.findProperty("animeal.enableComposeCompilerReports") == "true") {
                args.add("-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$buildDirectoryPath/compose_metrics")
                args.add("-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$buildDirectoryPath/compose_metrics")
            }

            freeCompilerArgs = freeCompilerArgs + args.toList()
        }
    }
}