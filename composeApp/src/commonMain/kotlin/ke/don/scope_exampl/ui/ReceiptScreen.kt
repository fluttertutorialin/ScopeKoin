package ke.don.scope_exampl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ke.don.scope_exampl.datasource.ReceiptManager
import org.koin.compose.getKoin

class ReceiptScreen(private val scopeID: String): Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val koin = getKoin()
        val scope = remember { koin.getScope(scopeID) }
        val receiptManager: ReceiptManager = scope.get()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Receipt") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator?.pop()
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
            ReceiptScreenContent(
                modifier = Modifier.padding(innerPadding),
                receiptManager = receiptManager,
                onBackToShopping = {
                    navigator?.replaceAll(HomeScreen)
                    scope.close()
                }
            )
        }
    }
}

@Composable
fun ReceiptScreenContent(
    modifier: Modifier = Modifier,
    receiptManager: ReceiptManager,
    onBackToShopping: () -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        receiptManager.items.forEach {
            ShoppingItemComponent(
                item = it,
                onAddToCart = {},
                onRemoveFromCart = {},
                isAddedToCart = true,
                enabled = false
            )
        }

        Text(
            text = "Total: ${receiptManager.items.sumOf { it.price }}",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onBackToShopping
        ){
            Text("Back to shopping")
        }
    }
}