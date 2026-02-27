package ke.don.scope_exampl.di

import ke.don.scope_exampl.datasource.CartManager
import ke.don.scope_exampl.datasource.CheckoutScope
import ke.don.scope_exampl.datasource.PaymentManager
import ke.don.scope_exampl.datasource.ReceiptManager
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

val scopeModule = module {
    scope<CheckoutScope> {
        scopedOf(::CartManager)
        scopedOf(::PaymentManager)
        scopedOf(::ReceiptManager)
    }

}