package com.opal.app.presentation.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun OpalText(
    text: String,
    modifier: Modifier = Modifier,
    font: OpalFont = OpalFont.CalloutRegular,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.White,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = font.size,
        fontWeight = font.weight,
        lineHeight = font.lineHeight,
        textAlign = textAlign,
        color = color,
        maxLines = maxLines,
    )
}

sealed class OpalFont(
    val size: TextUnit,
    var lineHeight: TextUnit,
    val weight: FontWeight,
) {
    data object CalloutRegular : OpalFont(
        size = 16.sp,
        lineHeight = 22.sp,
        weight = FontWeight.W400,
    )

    data object LargeButtonTitle : OpalFont(
        size = 17.sp,
        lineHeight = 22.sp,
        weight = FontWeight.W400,
    )

    data object SmallButtonTitle : OpalFont(
        size = 13.sp,
        lineHeight = 18.sp,
        weight = FontWeight.W400,
    )

    data object CalloutSemibold : OpalFont(
        size = 16.sp,
        lineHeight = 22.sp,
        weight = FontWeight.W600,
    )

    data object Caption : OpalFont(
        size = 12.sp,
        lineHeight = 18.sp,
        weight = FontWeight.W500,
    )

    data object FootNoteSemibold : OpalFont(
        size = 13.sp,
        lineHeight = 18.sp,
        weight = FontWeight.W600,
    )

    data object BodyMedium : OpalFont(
        size = 17.sp,
        lineHeight = 22.sp,
        weight = FontWeight.W500,
    )

    data object Body : OpalFont(
        size = 13.sp,
        lineHeight = 18.sp,
        weight = FontWeight.W500,
    )
}
