package themes

import androidx.compose.ui.graphics.Color

object LightTheme : Theme {
    override val themeMode: ThemeMode = ThemeMode.LIGHT
    override val conversationBackground: Color = Color.White
    override val myMessageColors: MessageColors = MessageColors(
        messageBackground = Color(83, 101, 49),
        messageText = Color(254, 255, 245),
        highlightedText = Color(186, 206, 145),
        codeBackground = Color(92, 97, 75),
        codeText = Color(254, 255, 245)
    )
    override val othersMessageColors: MessageColors = MessageColors(
        messageBackground = Color(226, 228, 214),
        messageText = Color(66, 68, 54),
        highlightedText = Color(80, 96, 49),
        codeBackground = Color(252, 253, 247),
        codeText = Color(57, 56, 52)
    )
}