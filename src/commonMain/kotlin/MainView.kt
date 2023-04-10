import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import composables.Conversation
import data.exampleUiState
import platform.getPlatformWebsocket
import themes.DarkTheme
import themes.LightTheme
import themes.ThemeMode

@Composable
@Suppress("FunctionName")
fun MainView() {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val ws: Any? = remember { getPlatformWebsocket() }
    val themeMode = remember { mutableStateOf(ThemeMode.LIGHT) }
    val theme = remember(themeMode.value) {
        derivedStateOf {
            when(themeMode.value) {
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
            webSocket = ws
        )
    }
}