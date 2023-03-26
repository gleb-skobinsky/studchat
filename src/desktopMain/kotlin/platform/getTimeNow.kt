package platform

import java.util.*

actual fun getTimeNow(): String = Calendar.getInstance().time.toString()
