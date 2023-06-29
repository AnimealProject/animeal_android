package com.epmedu.animeal.foundation.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.epmedu.animeal.foundation.button.AnimealButton
import com.epmedu.animeal.foundation.preview.AnimealPreview
import com.epmedu.animeal.foundation.theme.AnimealTheme
import kotlin.math.max

@Composable
internal fun ColumnScope.AlertDialogBaselineLayout(
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)?,
    button: @Composable (() -> Unit)?
) {
    Layout(
        {
            title?.let { title ->
                Box(
                    TitlePadding
                        .layoutId("title")
                        .align(Alignment.Start)
                ) {
                    title()
                }
            }
            text?.let { text ->
                Box(
                    TextPadding
                        .layoutId("text")
                        .align(Alignment.Start)
                ) {
                    text()
                }
            }
            button?.let { button ->
                Box(
                    ButtonPadding
                        .layoutId("buttons")
                        .align(Alignment.Start)
                ) {
                    button()
                }
            }
        },
        Modifier.weight(1f, false)
    ) { measurables, constraints ->
        // Measure with loose constraints for height as we don't want the text to take up more
        // space than it needs
        val titlePlaceable = measurables.firstOrNull { it.layoutId == "title" }?.measure(
            constraints.copy(minHeight = 0)
        )
        val textPlaceable = measurables.firstOrNull { it.layoutId == "text" }?.measure(
            constraints.copy(minHeight = 0)
        )

        val buttonPlaceable = measurables.firstOrNull { it.layoutId == "buttons" }?.measure(
            constraints.copy(minHeight = 0)
        )

        val layoutWidth = max(
            buttonPlaceable?.width ?: 0,
            max(titlePlaceable?.width ?: 0, textPlaceable?.width ?: 0)
        )

        val firstTitleBaseline = titlePlaceable?.get(FirstBaseline)?.let { baseline ->
            if (baseline == AlignmentLine.Unspecified) null else baseline
        } ?: 0
        val lastTitleBaseline = titlePlaceable?.get(LastBaseline)?.let { baseline ->
            if (baseline == AlignmentLine.Unspecified) null else baseline
        } ?: 0

        val titleOffset = TitleBaselineDistanceFromTop.roundToPx()

        // Place the title so that its first baseline is titleOffset from the top
        val titlePositionY = titleOffset - firstTitleBaseline

        val firstTextBaseline = textPlaceable?.get(FirstBaseline)?.let { baseline ->
            if (baseline == AlignmentLine.Unspecified) null else baseline
        } ?: 0

        val textOffset = if (titlePlaceable == null) {
            TextBaselineDistanceFromTop.roundToPx()
        } else {
            TextBaselineDistanceFromTitle.roundToPx()
        }

        // Combined height of title and spacing above
        val titleHeightWithSpacing = titlePlaceable?.let { it.height + titlePositionY } ?: 0

        // Align the bottom baseline of the text with the bottom baseline of the title, and then
        // add the offset
        val textPositionY = if (titlePlaceable == null) {
            // If there is no title, just place the text offset from the top of the dialog
            textOffset - firstTextBaseline
        } else {
            if (lastTitleBaseline == 0) {
                // If `title` has no baseline, just place the text's baseline textOffset from the
                // bottom of the title
                titleHeightWithSpacing - firstTextBaseline + textOffset
            } else {
                // Otherwise place the text's baseline textOffset from the title's last baseline
                (titlePositionY + lastTitleBaseline) - firstTextBaseline + textOffset
            }
        }

        // Combined height of text and spacing above
        val textHeightWithSpacing = textPlaceable?.let {
            if (lastTitleBaseline == 0) {
                textPlaceable.height + textOffset - firstTextBaseline
            } else {
                textPlaceable.height + textOffset - firstTextBaseline -
                        ((titlePlaceable?.height ?: 0) - lastTitleBaseline)
            }
        } ?: 0

        val buttonOffset = ButtonBaselineDistanceFromTop.roundToPx()

        // Align the bottom baseline of the button with the bottom baseline of the text, and then
        // add the offset
        val buttonPositionY = textPositionY + textHeightWithSpacing - textOffset + buttonOffset

        val buttonHeightWithSpacing = buttonPlaceable?.let {
            buttonPlaceable.height + buttonOffset
        } ?: 0

        val layoutHeight = titleHeightWithSpacing + textHeightWithSpacing + buttonHeightWithSpacing

        layout(layoutWidth, layoutHeight) {
            titlePlaceable?.place(0, titlePositionY)
            textPlaceable?.place(0, textPositionY)
            buttonPlaceable?.place(0, buttonPositionY)
        }
    }
}

@Composable
internal fun AlertDialogContent(
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Column {
            AlertDialogBaselineLayout(
                title = title?.let {
                    @Composable {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                            val textStyle = MaterialTheme.typography.subtitle1
                            ProvideTextStyle(textStyle, title)
                        }
                    }
                },
                text = text?.let {
                    @Composable {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.medium
                        ) {
                            val textStyle = MaterialTheme.typography.body2
                            ProvideTextStyle(textStyle, text)
                        }
                    }
                },
                button = buttons
            )
        }
    }
}

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        AlertDialogContent(
            buttons = buttons,
            modifier = modifier,
            title = title,
            text = text,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }
}

private val TitlePadding = Modifier.padding(start = 24.dp, end = 24.dp)
private val TextPadding = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
private val ButtonPadding = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)

// Baseline distance from the first line of the title to the top of the dialog
private val TitleBaselineDistanceFromTop = 40.sp

// Baseline distance from the first line of the text to the last line of the title
private val TextBaselineDistanceFromTitle = 36.sp

// For dialogs with no title, baseline distance from the first line of the text to the top of the
// dialog
private val TextBaselineDistanceFromTop = 38.sp

// Baseline distance from the button to the top
private val ButtonBaselineDistanceFromTop = 20.sp


@AnimealPreview
@Composable
private fun AnimealAlertDialogPreview() {
    AnimealTheme {
        AlertDialog(
            title = {
                Text(
                    text = "Title",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                )
            },
            text = {
                Text(
                    text = "Text",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                )
            },
            buttons = {
                AnimealButton(
                    text = "Button Text",
                    onClick = {}
                )
            },
            onDismissRequest = {}
        )
    }
}