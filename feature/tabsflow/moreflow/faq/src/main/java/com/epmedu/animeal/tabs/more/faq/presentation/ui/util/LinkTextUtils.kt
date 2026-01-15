package com.epmedu.animeal.tabs.more.faq.presentation.ui.util

import android.text.SpannableString
import android.text.style.URLSpan
import android.text.util.Linkify
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import com.epmedu.animeal.foundation.text.getUrlStyle

/**
 * Converts a plain String into an AnnotatedString with clickable links.
 *
 * - Uses Android's Linkify to detect links (URLs, emails, phone numbers)
 * - Converts detected links into Compose LinkAnnotations
 * - Applies link styling (color + underline)
 */
fun String.toAnnotatedLinkString(): AnnotatedString {
    val spannable = SpannableString(this)

    Linkify.addLinks(
        spannable,
        Linkify.WEB_URLS or
            Linkify.EMAIL_ADDRESSES or
            Linkify.PHONE_NUMBERS
    )

    val builder = AnnotatedString.Builder(this)

    spannable.getSpans(0, length, URLSpan::class.java).forEach { span ->
        val start = spannable.getSpanStart(span)
        val end = spannable.getSpanEnd(span)

        builder.addLink(
            LinkAnnotation.Url(
                url = span.url,
                styles = TextLinkStyles(
                    style = getUrlStyle()
                )
            ),
            start,
            end
        )
    }

    return builder.toAnnotatedString()
}
