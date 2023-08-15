package com.epmedu.animeal.foundation.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.withResumed

/**
 * Launches [onDisplay] lambda on first component composition
 * when [Lifecycle.State] is at least [Lifecycle.State.RESUMED].
 */
@Composable
fun DisplayedEffect(onDisplay: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.withResumed {
            onDisplay()
        }
    }
}