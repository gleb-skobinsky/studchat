import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import data.AdditionalUiState
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
            val uiState = AdditionalUiState()
            ThemeWrapper(uiState, coroutineScope, scrollState, ws)
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