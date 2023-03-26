package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun painterResourceMultiplatform(name: Any): Painter {
    return painterResource(name as Int)
}