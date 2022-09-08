package com.epmedu.animeal.foundation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.epmedu.animeal.resources.R

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

val Typography = Typography(
    defaultFontFamily = interFontFamily,
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    )
)