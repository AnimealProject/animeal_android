package com.epmedu.animeal.internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal val Project.libraryExtension
    get() = the<LibraryExtension>()

internal val Project.applicationExtension
    get() = the<ApplicationExtension>()

fun Project.libraryExtension(action: LibraryExtension.() -> Unit) = libraryExtension.run(action)

fun Project.applicationExtension(action: ApplicationExtension.() -> Unit) =
    applicationExtension.run(action)