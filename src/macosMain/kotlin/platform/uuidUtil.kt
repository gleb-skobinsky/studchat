package platform

import kotlinx.cinterop.*

actual fun generateUuid(): String = {
// Allocate memory for the UUID
    val uuid = ByteArray(16)
    memScoped {
// Generate the UUID
        uuid_generate(uuid.ref)

// Convert the UUID to a CFUUIDRef
        val cfuuid = CFUUIDCreateFromUUIDBytes(null, uuid.ref)

// Convert the CFUUIDRef to a string
        val cfstring = CFUUIDCreateString(null, cfuuid)
        val str = CFStringGetCStringPtr(cfstring, kCFStringEncodingUTF8)
        return str?.toKString() ?: throw IllegalStateException("Failed to generate UUID")
    }
}