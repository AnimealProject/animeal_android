import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.plugin.buildtools)
        classpath(libs.gradle.plugin.kotlin)
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
    }

    extra.apply {
        set("amplifyFramework", "1.36.0-dev-preview.0")
        set("desugarJdkLibs", "1.1.5")
        set("composeVersion", "1.1.1")
        set("activityComposeVersion", "1.4.0")
        set("navigationComposeVersion", "2.4.2")
        set("hiltAndroidVersion", "2.38.1")
        set("coreKtxVersion", "1.7.0")
        set("lifecycleKtxVersion", "2.4.1")
        set("junitVersion", "4.13.2")
        set("junitExtVersion", "1.1.3")
        set("espressoVersion", "3.4.0")
        set("viewModelVersion", "2.5.0-rc01")
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
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}