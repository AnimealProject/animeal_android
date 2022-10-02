package com.epmedu.animeal

import com.epmedu.animeal.internal.applicationExtension
import com.epmedu.animeal.internal.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import javax.inject.Inject

open class AnimealApplicationExtension @Inject constructor(private val project: Project) {

    /**
     * configure BuildConfig generation
     */
    var buildConfigGeneration = false
        set(value) {
            field = value
            project.applicationExtension {
                buildFeatures {
                    buildConfig = value
                }
            }
        }

    /**
     * configure Compose feature
     */
    var compose = false
        set(value) {
            field = value

            project.run {
                applicationExtension {
                    if (value) {
                        buildFeatures {
                            compose = true

                            composeOptions {
                                kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
                            }
                        }
                    }
                }
            }
        }

    companion object {

        fun register(project: Project): AnimealApplicationExtension =
            project.extensions.create("animealPlugin")
    }
}