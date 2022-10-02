import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply(plugin = "com.github.ben-manes.versions")

plugins {
    id("io.gitlab.arturbosch.detekt") version libs.versions.detekt
}

buildscript {
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
        delete(buildDir)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("animeal.enableComposeCompilerReports") == "true") {
                freeCompilerArgs = freeCompilerArgs +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_metrics" +
                        "-P=plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_metrics"
            }
        }
    }
}