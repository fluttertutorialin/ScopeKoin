package ke.don.scope_exampl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin
import kotlin.uuid.ExperimentalUuidApi

val checkoutModule = module {
    scope(named("CHECKOUT_SCOPE")) {
        scoped { CartManager() }
        scoped { PaymentManager(get()) }
        scoped { ReceiptManager(get()) }
    }

    viewModel { (scopeId: String) ->
        CheckoutViewModel(scopeId)
    }
}

class CartManager {
    private val items = mutableListOf<String>()

    fun addItem(item: String) {
        items.add(item)
    }

    fun getItems(): List<String> = items

    fun clear() {
        items.clear()
    }
}

class PaymentManager(
    private val cartManager: CartManager
) {
    fun pay(): Boolean {
        return cartManager.getItems().isNotEmpty()
    }
}

class ReceiptManager(
    private val cartManager: CartManager
) {
    fun generateReceipt(): String {
        return "Receipt: ${cartManager.getItems().joinToString()}"
    }
}

class CheckoutViewModel(scopeId: String) : ViewModel(), KoinScopeComponent {
    override val scope: Scope = getKoin().createScope(scopeId, named("CHECKOUT_SCOPE"))

    private val cartManager: CartManager by scope.inject()
    private val paymentManager: PaymentManager by scope.inject()
    private val receiptManager: ReceiptManager by scope.inject()

    // Cart
    fun addItem(item: String) {
        cartManager.addItem(item)
    }

    fun getItems(): List<String> {
        return cartManager.getItems()
    }

    // Payment
    fun pay(): Boolean {
        return paymentManager.pay()
    }

    // Receipt
    fun getReceipt(): String {
        return receiptManager.generateReceipt()
    }

    override fun onCleared() {
        super.onCleared()
        scope.close()   // Important!
    }
}


@OptIn(ExperimentalUuidApi::class)
@Composable
fun CheckoutScreen () {
    //val scopeId = remember { "checkout_${Uuid.random()}" } //kotlin.random.Random.nextInt()}
    val scopeId = remember { "checkout" } //static scope id

    val viewModel: CheckoutViewModel = koinViewModel(
        key = scopeId,
        parameters = { parametersOf(scopeId) }
    )

    Column {
        Button(onClick = {
            viewModel.addItem("Item")
        }) {
            Text("Add Item")
        }

        Button(onClick = {
            val result = viewModel.pay()
            println("Paid: $result")
        }) {
            Text("Pay")
        }

        Button(onClick = {
            println(viewModel.getReceipt())
        }) {
            Text("Generate Receipt")
        }
    }
}