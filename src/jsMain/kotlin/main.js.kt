import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import composables.Conversation
import data.exampleUiState
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.WheelEvent
import platform.getPlatformWebsocket

fun main() {
    resizeCanvas()
    onWasmReady {
        Window {
            val coroutineScope = rememberCoroutineScope()
            val scrollCoroutine = rememberCoroutineScope()
            val scrollState = rememberLazyListState()
            val ws: Any? = remember { getPlatformWebsocket() }
            Column {
                Conversation(exampleUiState, coroutineScope, scrollState, ws)
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