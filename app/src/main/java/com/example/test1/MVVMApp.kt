package com.example.mvvm

import android.app.Application
import android.content.Context
import com.example.mvvm.data.koin.dataModule
import com.example.mvvm.koin.viewModelModule
import com.example.mvvm.domain.service.LanguageService
import com.example.mvvm.language.LanguageAwareAppContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MVVMApp : Application() {
    private val languageService by inject<LanguageService>()
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageAwareAppContext(base, application = this))
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MVVMApp)
            modules(
                dataModule,
                viewModelModule
            )
        }
        languageService
            .languageFlow
            .onEach {
                (baseContext as LanguageAwareAppContext).appLanguage = it
            }
            .launchIn(CoroutineScope(Dispatchers.Main))
    }
}