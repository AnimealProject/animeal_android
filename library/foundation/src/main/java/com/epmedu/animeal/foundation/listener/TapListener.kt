package com.epmedu.animeal.foundation.listener

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun TapListener(
    content: @Composable (isTappedOutside: Boolean, onTappedOutsideHandle: () -> Unit) -> Unit
) {
    var isTapped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isTapped = true
                    }
                )
            }
    ) {
        content(isTapped) { isTapped = false }
    }
}