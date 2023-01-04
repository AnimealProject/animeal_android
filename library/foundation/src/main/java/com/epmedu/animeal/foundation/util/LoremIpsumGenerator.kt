package com.epmedu.animeal.foundation.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import kotlin.random.Random

/**
 * Generates Lorem Ipsum text with random length in specified [wordsCountRange]
 * @param wordsCountRange Words count range for randomizer. By default 5..100
 */
fun generateLoremIpsum(wordsCountRange: IntRange = 5..100) =
    LoremIpsum(
        words = Random.nextInt(
            from = wordsCountRange.first,
            until = wordsCountRange.last
        )
    ).values.joinToString()