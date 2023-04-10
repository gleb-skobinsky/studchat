import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import composables.Conversation
import data.AdditionalUiState
import data.exampleUiState
import platform.getPlatformWebsocket
import themes.DarkTheme
import themes.LightTheme
import themes.ThemeMode

@Composable
@Suppress("FunctionName")
fun MainView(uiState: AdditionalUiState) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val ws: Any? = remember { getPlatformWebsocket() }
    val themeMode by uiState.themeMode.collectAsState()
    val theme = remember(themeMode) {
        derivedStateOf {
            when (themeMode) {
                ThemeMode.LIGHT -> LightTheme
                ThemeMode.DARK -> DarkTheme
            }
        }
    }
    Column {
        Conversation(
            conversationUiState = exampleUiState,
            themeState = theme,
            scope = coroutineScope,
            scrollState = scrollState,
            webSocket = ws,
            uiState = uiState
        )
    }
}