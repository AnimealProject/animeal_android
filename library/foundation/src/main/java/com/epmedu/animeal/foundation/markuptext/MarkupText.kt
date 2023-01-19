package com.epmedu.animeal.foundation.markuptext

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.ireward.htmlcompose.HtmlText

@Composable
@Suppress("LongParameterList")
fun MarkupText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    alpha: Float = 1f,
    urlSpanStyle: SpanStyle = SpanStyle(
        color = Color.Cyan.copy(alpha = alpha),
        textDecoration = TextDecoration.Underline
    )
) {
    /** HtmlText doesn't add dots and numbers for list items, so that should be done manually */
    var content = UnorderedListParser.parse(text)
    content = OrderedListParser.parse(content)

    HtmlText(
        text = content,
        modifier = modifier,
        fontSize = fontSize,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style,
        URLSpanStyle = urlSpanStyle
    )
}