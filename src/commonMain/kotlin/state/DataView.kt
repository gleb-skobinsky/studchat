package org.jetbrains.studchat.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

data class DataView (
    val prompt: MutableStateFlow<String> = MutableStateFlow(""),
)