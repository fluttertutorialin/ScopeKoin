package ke.don.scope_exampl.di


import ke.don.scope_exampl.ui.checkoutModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            scopeModule,
            checkoutModule,
        )
    }
}
