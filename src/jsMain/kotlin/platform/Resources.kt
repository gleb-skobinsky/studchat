package platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.browser.window
import org.jetbrains.skia.Image

@Composable
actual fun painterResourceMultiplatform(name: Any): Painter {
    val host = "${window.location.protocol}//${window.location.host}/"
    val resourceUrl = "$host${name as String}"
    val loading = loadImage(resourceUrl).value

    return if (loading is ImageLoading.Loaded) {
        BitmapPainter(loading.image)
    } else {
        return BitmapPainter(ImageBitmap(100, 100))
    }
}


@Composable
internal fun loadImage(url: String): State<ImageLoading> = produceState<ImageLoading>(ImageLoading.Loading, url) {
    try {
        value = ImageLoading.Loaded(
            Image.makeFromEncoded(HttpClient().get(url).readBytes()).toComposeImageBitmap()
        )
    } catch (e: Exception) {
        println(url)
        e.printStackTrace()
    }
}

sealed class ImageLoading {
    object Loading : ImageLoading()
    data class Loaded(val image: ImageBitmap) : ImageLoading()
}