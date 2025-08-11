package com.epmedu.animeal.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId

fun Modifier.testTagAsResourceId(tag: String): Modifier =
    this.then(
        Modifier.semantics {
            testTag = tag
            testTagsAsResourceId = true
        }
    )