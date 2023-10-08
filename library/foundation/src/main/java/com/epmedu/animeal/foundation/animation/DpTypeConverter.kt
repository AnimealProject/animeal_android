package com.epmedu.animeal.foundation.animation

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun getDpTypeConverter(): TwoWayConverter<Dp, AnimationVector1D> {
    val density = LocalDensity.current

    return TwoWayConverter(
        { AnimationVector(with(density) { it.toPx() }) },
        { with(density) { it.value.toDp() } }
    )
}