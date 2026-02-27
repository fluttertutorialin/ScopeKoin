package ke.don.scope_exampl

import android.app.Application
import ke.don.scope_exampl.di.initKoin
import org.koin.android.ext.koin.androidContext

class ScopeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@ScopeApplication)
        }
    }
}