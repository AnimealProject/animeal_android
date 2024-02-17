plugins {
    id("AnimealPlugin")
    id("com.android.library")
}

android {
    namespace = "com.epmedu.animeal.tabs.more"
}

dependencies {
    implementation(projects.feature.tabsflow.moreflow.about)
    implementation(projects.feature.tabsflow.moreflow.account)
    implementation(projects.feature.tabsflow.moreflow.donate)
    implementation(projects.feature.tabsflow.moreflow.faq)
    implementation(projects.feature.tabsflow.moreflow.more)
    implementation(projects.feature.tabsflow.moreflow.profile)

    implementation(projects.library.common)
    implementation(projects.library.navigation)

    implementation(projects.shared.feature.feedings)
}