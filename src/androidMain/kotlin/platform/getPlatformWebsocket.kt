package platform

import composables.Message
import data.exampleUiState
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*

actual fun getPlatformWebsocket(): Any? {
    val client by lazy { OkHttpClient() }
    val request: Request = Request.Builder().url("ws://10.0.2.2:8082/").build()
    val listener = object: WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            val currentTime: Date = Calendar.getInstance().time
            exampleUiState.addMessage(Message(generateUuid(), "Web", text, currentTime.toString()))
        }
    }
    return client.newWebSocket(request, listener)
}

actual fun onMessageEnter(message: Message, ws: Any) {
    (ws as WebSocket).send(message.content)
}