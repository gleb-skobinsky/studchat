package platform

import composables.Message

actual fun getPlatformWebsocket(): Any? = null

actual fun onMessageEnter(message: Message, ws: Any) {}