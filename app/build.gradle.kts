import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int

        applicationId = "com.epmedu.animeal"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["composeVersion"] as String
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

    project.tasks.preBuild.dependsOn("moveSecretsToProject")
}

dependencies {
    implementation(project(":base"))

    val amplifyFramework: String by rootProject.extra
    val desugarJdkLibs: String by rootProject.extra
    val activityComposeVersion: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val coreKtxVersion: String by rootProject.extra
    val espressoVersion: String by rootProject.extra
    val junitExtVersion: String by rootProject.extra
    val junitVersion: String by rootProject.extra
    val hiltAndroidVersion: String by rootProject.extra
    val lifecycleKtxVersion: String by rootProject.extra

    // Amplify core dependency
    implementation("com.amplifyframework:core:$amplifyFramework")

    // Support for Java 8 features
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarJdkLibs")

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVersion")

    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltAndroidVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}


val AWS_CONFIGURATION: String by project
val AMPLIFY_CONFIGURATION: String by project

try {
    //checking project configuration
    AWS_CONFIGURATION
    AMPLIFY_CONFIGURATION
} catch (e: Exception) {
    throw Exception("!!!!!!! project-secrets not located; please follow instructions in the knowledge-base to ensure proper environment setup", e)
}

tasks.register("moveSecretsToProject") {
    dependsOn(tasks.named("copyAwsAmplifySecrets"))
}

tasks.register<Copy>("copyAwsAmplifySecrets") {
    from(
        AWS_CONFIGURATION,
        AMPLIFY_CONFIGURATION
    )
    into("${rootDir}/app/src/main/res/raw")
}