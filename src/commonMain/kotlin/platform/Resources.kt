package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

//@Composable
//expect fun font(res: String, weight: FontWeight, style: FontStyle): Font

@Composable
expect fun painterResourceMultiplatform(name: Any): Painter