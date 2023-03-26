package platform

import composables.Message

expect fun getPlatformWebsocket(): Any?

expect fun onMessageEnter(message: Message, ws: Any)

//{
//    ws.send(message.content)
//}