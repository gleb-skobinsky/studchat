package platform

import androidx.compose.runtime.Composable
import composables.CommonThemeSwitch
import data.AdditionalUiState

@Composable
actual fun ThemeSwitch(uiState: AdditionalUiState, onThemeChange: (Boolean) -> Unit) {
    CommonThemeSwitch(uiState, onThemeChange)
}
