package composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import data.AdditionalUiState
import data.ConversationUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.generateUuid
import platform.getTimeNow
import platform.onMessageEnter
import platform.userInputModifier
import themes.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Conversation(
    conversationUiState: ConversationUiState,
    scope: CoroutineScope,
    scrollState: LazyListState,
    webSocket: Any?,
    themeState: State<Theme>,
    uiState: AdditionalUiState
) {
    val scaffoldState = rememberScaffoldState()
    val drawerOpen by uiState.drawerShouldBeOpened.collectAsState()
    if (drawerOpen) {
        // Open drawer and reset state in VM.
        LaunchedEffect(Unit) {
            scaffoldState.drawerState.open()
            uiState.resetOpenDrawerAction()
        }
    }

    JetchatScaffold(
        scaffoldState,
        onChatClicked = {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        },
        onProfileClicked = {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }
    ) {
        ConversationContent(
            conversationUiState = conversationUiState,
            scrollState = scrollState,
            themeState = themeState,
            webSocket = webSocket,
            scope = scope,
            onNavIconPressed = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationContent(
    conversationUiState: ConversationUiState,
    scrollState: LazyListState,
    themeState: State<Theme>,
    webSocket: Any?,
    scope: CoroutineScope,
    onNavIconPressed: () -> Unit,
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
                modifier = Modifier.userInputModifier(),
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