package ke.don.scope_exampl.di

import ke.don.scope_exampl.ui.checkoutModule
import org.koin.core.annotation.KoinViewModelScopeApi
import org.koin.core.context.startKoin
import org.koin.core.option.viewModelScopeFactory
import org.koin.dsl.KoinAppDeclaration
@OptIn(KoinViewModelScopeApi::class)
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        options(
            viewModelScopeFactory()
        )

        config?.invoke(this)
        modules(
            scopeModule,
            checkoutModule
        )
    }
}

