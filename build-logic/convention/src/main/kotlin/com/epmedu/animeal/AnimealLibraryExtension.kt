package com.epmedu.animeal

import com.epmedu.animeal.internal.libraryExtension
import com.epmedu.animeal.internal.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import javax.inject.Inject

open class AnimealLibraryExtension @Inject constructor(private val project: Project) {

    /**
     * configure BuildConfig generation
     */
    var buildConfigGeneration = false
        set(value) {
            field = value
            project.libraryExtension.run {
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
                project.libraryExtension {
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

        fun register(project: Project): AnimealLibraryExtension =
            project.extensions.create("animealPlugin")
    }
}