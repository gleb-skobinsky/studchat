package platform

import java.util.*

actual fun generateUuid(): String = UUID.randomUUID().toString()