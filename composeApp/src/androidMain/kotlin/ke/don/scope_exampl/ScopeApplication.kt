package ke.don.scope_exampl

import android.app.Application
import ke.don.scope_exampl.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication

class ScopeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

    }
}