package platform

import kotlin.js.Date

actual fun getTimeNow(): String = Date().toTimeString()