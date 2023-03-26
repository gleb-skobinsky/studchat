package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import data.ConversationUiState
import platform.generateUuid
import platform.getTimeNow
import platform.onMessageEnter

@Composable
fun Conversation(
    conversationUiState: ConversationUiState,
    scope: CoroutineScope,
    scrollState: LazyListState,
    webSocket: Any?
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Messages(conversationUiState, scrollState)
        Column(Modifier.align(Alignment.BottomCenter)) {
            UserInput(
                onMessageSent = { content ->
                    val timeNow = getTimeNow()
                    val message = Message(generateUuid(), "me", content, timeNow)
                    webSocket?.let{ onMessageEnter(message, it) }
                    conversationUiState.addMessage(message)
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // Use navigationBarsWithImePadding(), to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier // .navigationBarsWithImePadding(),
            )
        }
    }
}