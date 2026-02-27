package ke.don.scope_exampl

import androidx.compose.ui.window.ComposeUIViewController
import ke.don.scope_exampl.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}