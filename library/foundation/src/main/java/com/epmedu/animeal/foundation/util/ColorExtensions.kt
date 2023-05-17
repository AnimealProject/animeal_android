package com.epmedu.animeal.foundation.util

import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Color.withLocalAlpha() = copy(alpha = LocalContentAlpha.current)