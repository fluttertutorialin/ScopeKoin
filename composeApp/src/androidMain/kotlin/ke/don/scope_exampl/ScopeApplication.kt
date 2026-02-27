package ke.don.scope_exampl

import android.app.Application
import org.koin.core.component.KoinComponent

class ScopeApplication: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
    }
}