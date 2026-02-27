package ke.don.scope_exampl.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import ke.don.scope_exampl.datasource.CheckoutScope

object HomeScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            CheckoutScreen()

            Button(
                onClick = { navigator?.push(CheckoutFlow) }
            ){
                Text("Start Checkout")
            }
        }
    }
}





object CheckoutFlow : Screen {
    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val scope = remember { CheckoutScope() }

        Navigator(
            screen = CartScreen(scope.scope.id),
        ){ navigator ->
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

        BackHandler{
            scope.close()
        }
    }
}

