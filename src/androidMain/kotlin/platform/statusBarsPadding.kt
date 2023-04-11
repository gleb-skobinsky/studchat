package platform

import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding

actual fun Modifier.statusBarsPaddingMpp(): Modifier = this.statusBarsPadding()