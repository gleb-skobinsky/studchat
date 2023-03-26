package platform

external fun require(module: String): dynamic
val uuid = require("uuid")
actual fun generateUuid(): String = uuid.v4() as String