package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import data.ConversationUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.generateUuid
import platform.getTimeNow
import platform.onMessageEnter
import themes.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Conversation(
    conversationUiState: ConversationUiState,
    scope: CoroutineScope,
    scrollState: LazyListState,
    webSocket: Any?,
    onNavIconPressed: () -> Unit = { },
    themeState: State<Theme>,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Box(modifier = Modifier.fillMaxSize()) {
        Messages(conversationUiState, scrollState, themeState)
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            UserInput(
                onMessageSent = { content ->
                    val timeNow = getTimeNow()
                    val message = Message(generateUuid(), "me", content, timeNow)
                    webSocket?.let { onMessageEnter(message, it) }
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
        ChannelNameBar(
            channelName = conversationUiState.channelName,
            channelMembers = conversationUiState.channelMembers,
            onNavIconPressed = onNavIconPressed,
            scrollBehavior = scrollBehavior,
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier,
        )

    }
}