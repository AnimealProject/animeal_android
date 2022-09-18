plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

dependencies {
    implementation(projects.feature.mainflow.moreflow.about)
    implementation(projects.feature.mainflow.moreflow.account)
    implementation(projects.feature.mainflow.moreflow.donate)
    implementation(projects.feature.mainflow.moreflow.help)
    implementation(projects.feature.mainflow.moreflow.more)
    implementation(projects.feature.more)

    implementation(projects.library.navigation)
}