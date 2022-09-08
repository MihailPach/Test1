package com.example.mvvm.koin

import com.example.mvvm.list.ListViewModel
import com.example.mvvm.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.mvvm.language.LanguageViewModel
import com.example.mvvm.nightmode.NightModeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ListViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::NightModeViewModel)
    viewModelOf(::LanguageViewModel)
}