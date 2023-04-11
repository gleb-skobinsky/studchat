package platform

import androidx.compose.ui.Modifier
import com.google.accompanist.insets.navigationBarsWithImePadding

actual fun Modifier.userInputModifier(): Modifier = this.navigationBarsWithImePadding()