package org.jetbrains.studchat.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import composables.Conversation
import data.exampleUiState
import org.jetbrains.studchat.messagesParser.*
import platform.getPlatformWebsocket

// private val client by lazy { OkHttpClient() }

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




