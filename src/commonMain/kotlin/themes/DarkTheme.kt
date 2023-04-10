package themes

import androidx.compose.ui.graphics.Color

object DarkTheme : Theme {
    override val themeMode: ThemeMode = ThemeMode.DARK
    override val conversationBackground: Color = Color.White
    override val myMessageColors: MessageColors = MessageColors(
        messageBackground = Color(185, 206, 139),
        messageText = Color(29, 47, 0),
        highlightedText = Color(82, 101, 45),
        codeBackground = Color(196, 202, 174),
        codeText = Color(29, 39, 5)
    )
    override val othersMessageColors: MessageColors = MessageColors(
        messageBackground = Color(70, 72, 61),
        messageText = Color(201, 203, 190),
        highlightedText = Color(186, 203, 151),
        codeBackground = Color(27, 28, 23),
        codeText = Color(207, 208, 200)
    )
}