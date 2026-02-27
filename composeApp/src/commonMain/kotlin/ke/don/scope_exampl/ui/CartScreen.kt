package ke.don.scope_exampl.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ke.don.scope_exampl.datasource.CartManager
import ke.don.scope_exampl.datasource.CheckoutScope
import ke.don.scope_exampl.datasource.ShoppingItem
import ke.don.scope_exampl.datasource.shoppingItems
import org.koin.compose.getKoin
import org.koin.core.component.get


class CartScreen(
    private val scopeID: String
): Screen {
    @OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val koin = getKoin()
        val scope = remember { koin.getScope(scopeID) }
        val cartManager: CartManager = scope.get()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Cart") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.replaceAll(HomeScreen)
                            }
                        ){
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
        ){ innerPadding ->
            CartScreenContent(
                modifier = Modifier.padding(innerPadding),
                onProceedToCheckout = { navigator?.push(PaymentScreen(scopeID)) },
                cartManager = cartManager
            )
        }
    }

}

@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    cartManager: CartManager,
    onProceedToCheckout: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        val items = cartManager.items  // no need for remember or mutableStateOf

        shoppingItems.forEach {
            ShoppingItemComponent(
                item = it,
                onAddToCart = { cartManager.addToCart(it) },
                onRemoveFromCart = { cartManager.removeFromCart(it) },
                isAddedToCart = it in items
            )
        }

        Text(
            text = "Total: ${items.sumOf { it.price }}",
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = onProceedToCheckout) {
            Text("Go to Checkout")
        }
    }

}

@Composable
fun ShoppingItemComponent(
    item: ShoppingItem,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    isAddedToCart: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Ksh ${item.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AnimatedContent(
            targetState = isAddedToCart,
            label = "cart_button_transition"
        ) { added ->
            Button(
                enabled = enabled,
                onClick = if (added) onRemoveFromCart else onAddToCart,
                modifier = Modifier.weight(0.6f)
            ) {
                Text(if (added) "Remove" else "Add to cart")
            }
        }
    }
}

