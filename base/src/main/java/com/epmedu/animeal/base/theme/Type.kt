package com.epmedu.animeal.base.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.base.R

val interFontFamily =
    FontFamily(
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_black, FontWeight.Black),
        Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_extra_light, FontWeight.ExtraLight),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_thin, FontWeight.Thin),
    )

// Set of Material typography styles to start with
val Typography = Typography(
    h4 = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    body1 = TextStyle(
        fontFamily = interFontFamily
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