package platform

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import composables.CommonThemeSwitch
import data.AdditionalUiState
import themes.toBoolean

@Composable
actual fun ThemeSwitch(uiState: AdditionalUiState, onThemeChange: (Boolean) -> Unit) {
    CommonThemeSwitch(uiState, onThemeChange)
}
