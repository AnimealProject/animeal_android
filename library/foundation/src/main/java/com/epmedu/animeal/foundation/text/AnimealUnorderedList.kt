package com.epmedu.animeal.foundation.text

import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import com.halilibo.richtext.ui.FormattedList
import com.halilibo.richtext.ui.ListStyle
import com.halilibo.richtext.ui.ListType.Unordered
import com.halilibo.richtext.ui.RichTextScope
import com.halilibo.richtext.ui.RichTextStyle
import com.halilibo.richtext.ui.RichTextThemeProvider
import com.halilibo.richtext.ui.material.RichText

@Composable
fun AnimealUnorderedList(
    items: List<String>,
    modifier: Modifier = Modifier,
    drawItem: @Composable RichTextScope.(String) -> Unit
) {
    RichTextThemeProvider(
        contentColorProvider = { LocalContentColor.current }
    ) {
        RichText(
            modifier = modifier,
            style = RichTextStyle(
                paragraphSpacing = 0.sp,
                listStyle = ListStyle(
                    contentsIndent = 8.sp,
                    itemSpacing = 0.sp
                )
            )
        ) {
            FormattedList(
                listType = Unordered,
                items = items,
                drawItem = drawItem
            )
        }
    }
}

@AnimealPreview
@Composable
fun AnimealFormattedListPreview() {
    AnimealTheme {
        AnimealUnorderedList(items = listOf("First string", "Second string")) {
            Text(text = it)
        }
    }
}