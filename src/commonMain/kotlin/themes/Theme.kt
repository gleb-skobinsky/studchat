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