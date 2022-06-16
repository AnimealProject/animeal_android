import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("AnimealPlugin")
    id("com.android.application")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(projects.base)

    val amplifyFramework: String by rootProject.extra
    val desugarJdkLibs: String by rootProject.extra
    val activityComposeVersion: String by rootProject.extra
    val composeVersion: String by rootProject.extra
    val navigationComposeVersion: String by rootProject.extra
    val coreKtxVersion: String by rootProject.extra
    val espressoVersion: String by rootProject.extra
    val junitExtVersion: String by rootProject.extra
    val junitVersion: String by rootProject.extra
    val hiltAndroidVersion: String by rootProject.extra
    val lifecycleKtxVersion: String by rootProject.extra
    val viewModelVersion: String by rootProject.extra

    // Amplify core dependency
    implementation("com.amplifyframework:core:$amplifyFramework")

    // Support for Java 8 features
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarJdkLibs")

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleKtxVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelVersion")

    implementation("com.google.dagger:hilt-android:$hiltAndroidVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltAndroidVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")

    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitExtVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}