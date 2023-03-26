import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import composables.Conversation
import data.exampleUiState
import platform.getPlatformWebsocket

@Composable
@Suppress("FunctionName")
fun MainView() {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val ws: Any? = remember { getPlatformWebsocket() }
    Column {
        Conversation(exampleUiState, coroutineScope, scrollState, ws)
    }
}