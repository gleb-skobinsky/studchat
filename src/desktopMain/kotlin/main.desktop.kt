import androidx.compose.runtime.SideEffect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import data.AdditionalUiState

fun main() {
    val iconPainter = BitmapPainter(useResource("jetchat_icon.png", ::loadImageBitmap))
    singleWindowApplication(
        title = "StudChat",
        state = WindowState(
            placement = WindowPlacement.Maximized,
        ),
    ) {
        val density = LocalDensity.current
        SideEffect {
            window.iconImage = iconPainter.toAwtImage(density, LayoutDirection.Ltr, Size(128f, 128f))
        }
        val uiState = AdditionalUiState()
        MainView(uiState)
    }
}