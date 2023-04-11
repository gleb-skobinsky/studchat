package com.example.studchat

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import data.AdditionalUiState
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyAssets()
        val uiState = AdditionalUiState()
        // _HomeFolder = filesDir
        setContent {
            val windowInsets = ViewWindowInsetObserver(ComposeView(LocalContext.current))
                .start(windowInsetsAnimationsEnabled = true)
            CompositionLocalProvider(
                LocalWindowInsets provides windowInsets,
            ) {
                MainView(uiState)
            }
        }
    }

    private fun copyAssets() {
        for (filename in assets.list("data")!!) {
            assets.open("data/$filename").use { assetStream ->
                val file = File(filesDir, filename)
                FileOutputStream(file).use { fileStream ->
                    assetStream.copyTo(fileStream)
                }
            }
        }
    }
}