import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import composables.Conversation
import data.AdditionalUiState
import data.exampleUiState
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.WheelEvent
import platform.getPlatformWebsocket
import themes.JetchatTheme

fun main() {
    resizeCanvas()
    onWasmReady {
        Window {
            val coroutineScope = rememberCoroutineScope()
            val scrollCoroutine = rememberCoroutineScope()
            val scrollState = rememberLazyListState()
            val ws: Any? = remember { getPlatformWebsocket() }
            val uiState = AdditionalUiState()
            val theme by uiState.themeMode.collectAsState()
            JetchatTheme(theme) {
                Column {
                    Conversation(exampleUiState, coroutineScope, scrollState, ws, uiState = uiState)
                }
            }
            window.addEventListener("wheel", {
                val delta = (it as WheelEvent).deltaY
                scrollCoroutine.launch {
                    scrollState.scroll {
                        scrollBy(-delta.toFloat())
                    }
                }
            })
        }
    }
}

private fun resizeCanvas() {
    val wasmCanvas = document.getElementById("ComposeTarget") as HTMLCanvasElement
    wasmCanvas.width = document.body?.clientWidth ?: window.innerWidth
    wasmCanvas.height = document.body?.scrollHeight ?: window.innerHeight
}