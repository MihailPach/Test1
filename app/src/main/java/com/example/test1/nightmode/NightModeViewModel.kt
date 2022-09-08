package com.example.mvvm.nightmode

import androidx.lifecycle.ViewModel
import com.example.mvvm.domain.model.settings.NightMode
import com.example.mvvm.domain.service.NightModeService

class NightModeViewModel(private val prefsService: NightModeService) : ViewModel() {

    var selectedNightMode: NightMode by prefsService::nightMode
}