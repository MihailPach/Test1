package com.example.mvvm.language

import androidx.lifecycle.ViewModel
import com.example.mvvm.domain.model.settings.Language
import com.example.mvvm.domain.service.LanguageService

class LanguageViewModel(private val languageService: LanguageService) : ViewModel() {

    var selectedLanguage: Language by languageService::language
}