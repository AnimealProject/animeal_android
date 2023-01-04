package com.epmedu.animeal.foundation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Density

/**
 * Sticks last child to the bottom.
 * Visually: (top) 12###3 (bottom)
 */
@Stable
val Arrangement.LastElementBottom: Arrangement.Vertical
    @Composable get() = remember {
        object : Arrangement.Vertical {
            override fun Density.arrange(
                totalSize: Int,
                sizes: IntArray,
                outPositions: IntArray
            ) {
                var currentOffset = 0
                sizes.forEachIndexed { index, size ->
                    if (index == sizes.lastIndex) {
                        outPositions[index] = totalSize - size
                    } else {
                        outPositions[index] = currentOffset
                        currentOffset += size
                    }
                }
            }
        }
    }