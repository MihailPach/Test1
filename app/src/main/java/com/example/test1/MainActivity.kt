package com.example.mvvm

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.example.mvvm.domain.model.settings.NightMode
import com.example.mvvm.domain.service.LanguageService
import com.example.mvvm.domain.service.NightModeService
import com.example.mvvm.extension.applySelectedAppLanguage
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val languageService by inject<LanguageService>()
    private val nightModeService by inject<NightModeService>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(languageService.language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialNightMode()
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun initialNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (nightModeService.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}