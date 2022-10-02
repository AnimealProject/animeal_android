@file:Suppress("unused")

import com.android.build.api.dsl.ApplicationExtension
import com.epmedu.animeal.AnimealApplicationExtension
import com.epmedu.animeal.configure.addMapBoxPublicKeyField
import com.epmedu.animeal.configure.addMapBoxStyleURIField
import com.epmedu.animeal.configure.provideVersionCode
import com.epmedu.animeal.configure.provideVersionName
import com.epmedu.animeal.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        AnimealApplicationExtension.register(target)

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.versions.compileSdk.get().toInt()

            defaultConfig {
                applicationId = "com.epmedu.animeal"
                targetSdk = libs.versions.targetSdk.get().toInt()
                minSdk = libs.versions.minSdk.get().toInt()
                versionCode = provideVersionCode()
                versionName = provideVersionName()

                addMapBoxPublicKeyField()
                addMapBoxStyleURIField()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        "proguard-rules.pro",
                        getDefaultProguardFile("proguard-android-optimize.txt")
                    )
                }

                debug {
                    isMinifyEnabled = false
                }
            }
        }
    }
}