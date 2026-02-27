package ke.don.scope_exampl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ke.don.scope_exampl.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ScopeExampl",
    ) {
        initKoin()
        App()
    }
}