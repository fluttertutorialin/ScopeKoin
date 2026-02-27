package ke.don.scope_exampl

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import ke.don.scope_exampl.di.initKoin
import ke.don.scope_exampl.di.scopeModule
import ke.don.scope_exampl.ui.HomeScreen
import ke.don.scope_exampl.ui.checkoutModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    KoinApplication(configuration = KoinConfiguration(
        config = { initKoin() }
    ), content = {
        MaterialTheme {
            Navigator(HomeScreen) { navigator ->
                AnimatedContent(
                    targetState = navigator.lastItem,
                    transitionSpec = {
                        if (navigator.lastEvent == StackEvent.Pop) {
                            (scaleIn(initialScale = 1.2f) + fadeIn()) togetherWith
                                    (scaleOut(targetScale = 0.8f) + fadeOut())
                        } else {
                            (scaleIn(initialScale = 0.8f) + fadeIn()) togetherWith
                                    (scaleOut(targetScale = 1.2f) + fadeOut())
                        }
                    },
                ) { screen ->
                    screen.Content()
                }
            }
        }
    })
}


