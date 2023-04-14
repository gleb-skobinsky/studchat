package org.jetbrains.studchat.ui.style

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.style.ColorScheme

fun Modifier.innerShadow() = this.drawBehind {
    val stepsHeight = (size.height / 8).toInt()
    val stepsWidth = (size.width / 8).toInt()
    drawRoundRect(
        brush = Brush.verticalGradient(
            listOf(ColorScheme.textShadowColor) + List(stepsHeight) { Color.Transparent } + listOf(ColorScheme.textShadowColor2)
        ),
        cornerRadius = CornerRadius(12.dp.toPx())
    )
    drawRoundRect(

        brush = Brush.horizontalGradient(
            listOf(ColorScheme.textShadowColor) + List(stepsWidth) { Color.Transparent } + listOf(ColorScheme.textShadowColor2)
        ),
        cornerRadius = CornerRadius(12.dp.toPx())
    )
}