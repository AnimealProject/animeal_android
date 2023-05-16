package com.epmedu.animeal.foundation.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = LocalTextStyle.current,
    textSize: TextUnit = textStyle.fontSize,
    minTextSize: TextUnit = 10.sp,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
) {
    var fontSize by remember { mutableStateOf(textSize) }

    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize,
        style = textStyle,
        maxLines = maxLines,
        overflow = overflow,
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex) && fontSize > minTextSize) {
                fontSize *= 0.9f
            }
        },
    )
}
