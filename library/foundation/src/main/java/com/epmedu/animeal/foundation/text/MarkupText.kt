package com.epmedu.animeal.foundation.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.text.parser.OrderedListParser
import com.epmedu.animeal.foundation.text.parser.UnorderedListParser
import com.epmedu.animeal.foundation.theme.CustomColor
import com.ireward.htmlcompose.HtmlText

fun getUrlStyle(alpha: Float = 1f) =
    SpanStyle(
        color = CustomColor.LinkColor.copy(alpha = alpha),
        textDecoration = TextDecoration.Underline
    )

@Composable
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
    urlSpanStyle: SpanStyle = getUrlStyle(alpha)
) {
    /** HtmlText doesn't add dots and numbers for list items, so that should be done manually. */
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

fun buildLinkedAnnotatedString(
    text: String,
    linksMap: Map<String, String>,
    urlStyle: SpanStyle = getUrlStyle()
): AnnotatedString = buildAnnotatedString {
    var cursor = 0
    val regex = Regex("%([^%]+)%")
    val matches = regex.findAll(text)

    for (match in matches) {
        append(text.substring(cursor, match.range.first))

        val name = match.groupValues[1]
        val url = linksMap[name]

        if (url != null) {
            pushLink(LinkAnnotation.Url(url))
            withStyle(urlStyle) { append(name) }
            pop()
        } else {
            append(name)
        }

        cursor = match.range.last + 1
    }

    append(text.substring(cursor))
}