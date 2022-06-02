buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
    }

    extra.apply {
        set("compileSdk", 32)
        set("minSdk", 24)
        set("targetSdk", 32)
        set("composeVersion", "1.1.1")
        set("activityComposeVersion", "1.4.0")
        set("hiltAndroidVersion", "2.38.1")
        set("coreKtxVersion", "1.7.0")
        set("lifecycleKtxVersion", "2.4.1")
        set("junitVersion", "4.13.2")
        set("junitExtVersion", "1.1.3")
        set("espressoVersion", "3.4.0")
    }
}

tasks {
    registering(Delete::class) {
        delete(buildDir)
    }
}