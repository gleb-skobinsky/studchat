package platform

import androidx.compose.runtime.Composable
import composables.CommonThemeSwitch
import data.AdditionalUiState
import io.ktor.utils.io.*

@Composable
actual fun ThemeSwitch(uiState: AdditionalUiState, onThemeChange: (Boolean) -> Unit) {
    // CommonThemeSwitch(uiState, onThemeChange)
}
/*
Box(
    Modifier
        .defaultMinSize(300.dp, 48.dp)
        .fillMaxSize()
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(CircleShape)
    ) {
        val checkedState by uiState.themeMode.collectAsState()
        val boolChecked = checkedState.toBoolean()
        val iconColor = MaterialTheme.colorScheme.onSecondary
        val commonModifier = Modifier.align(Alignment.CenterVertically)
        Icon(
            imageVector = Icons.Outlined.LightMode,
            contentDescription = "Light theme",
            modifier = commonModifier,
            tint = iconColor
        )
        val switchPadding = if (boolChecked) 32.dp else 0.dp
        Row(
            commonModifier
                .size(64.dp, 32.dp)
                .background(Color.Cyan.copy(alpha = 0.6f), RoundedCornerShape(50))
        ) {
            Button(
                onClick = {
                    // onThemeChange(!boolChecked)
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(switchPadding).size(32.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)
            ) {}
        }
        Icon(
            imageVector = Icons.Outlined.DarkMode,
            contentDescription = "Dark theme",
            modifier = commonModifier,
            tint = iconColor
        )
    }
}
 */

