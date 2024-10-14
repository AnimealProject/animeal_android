package com.epmedu.animeal.foundation.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class GeorgianPhoneTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = buildAnnotatedString {
            for (i in text.indices) {
                when (i) {
                    3 -> append(" ")
                    5, 7 -> append("-")
                }
                append(text[i])
            }
        }
        return TransformedText(
            text = annotatedString,
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return when (offset) {
                        in 0..3 -> offset
                        in 4..5 -> offset + 1
                        in 6..7 -> offset + 2
                        in 8..9 -> offset + 3
                        else -> -1
                    }
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return when (offset) {
                        in 0..3 -> offset
                        in 5..6 -> offset - 1
                        in 8..9 -> offset - 2
                        in 11..12 -> offset - 3
                        else -> -1
                    }
                }
            }
        )
    }
}