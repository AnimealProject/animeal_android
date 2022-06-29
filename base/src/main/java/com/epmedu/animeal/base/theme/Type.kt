package com.epmedu.animeal.base.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

/**
 * Custom [FontFamily] from resources
 */
val helveticaFamily = FontFamily(
    Font(R.font.helvetica_semi_bold, FontWeight.SemiBold),
    Font(R.font.helvetica_regular, FontWeight.Normal),
    Font(R.font.helvetica_medium, FontWeight.Medium),
    Font(R.font.helvetica_bold, FontWeight.Bold),
)
