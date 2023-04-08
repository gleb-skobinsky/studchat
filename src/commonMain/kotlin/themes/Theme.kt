package themes

import androidx.compose.ui.graphics.Color

interface Theme {
    val themeMode: ThemeMode
    val conversationBackground: Color
    val myMessageColors: MessageColors
    val othersMessageColors: MessageColors
}

enum class ThemeMode {
    DARK,
    LIGHT
}

class MessageColors(
    val messageBackground: Color,
    val messageText: Color,
    val highlightedText: Color,
    val codeBackground: Color,
    val codeText: Color
)

object LightTheme : Theme {
    override val themeMode: ThemeMode = ThemeMode.LIGHT
    override val conversationBackground: Color = Color.White
    override val myMessageColors: MessageColors = MessageColors(
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        Color.White
    )
    override val othersMessageColors: MessageColors = MessageColors(
        Color.White,
        Color.White,
        Color.White,
        Color.White,
        Color.White
    )
}