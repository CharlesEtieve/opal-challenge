package com.opal.app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.opal.app.presentation.theme.White15
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsState(): State<T> = collectAsStateWithLifecycle()

// https://proandroiddev.com/add-modifier-conditionally-without-sacrificing-its-fluent-api-a572cc085fb2
fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier,
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }

fun Modifier.dashedBorder(
    width: Dp = 1.dp,
    color: Color = White15,
    cornerRadius: Dp = 16.dp,
    dashWidth: Dp = 4.dp,
    dashGap: Dp = 2.dp,
) = this.drawWithContent {
    drawContent()

    val strokeWidth = width.toPx()
    val dash = dashWidth.toPx()
    val gap = dashGap.toPx()

    drawRoundRect(
        color = color,
        style =
            Stroke(
                width = strokeWidth,
                pathEffect =
                    PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dash, gap),
                        phase = 0f,
                    ),
            ),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
    )
}
