package com.example.mvvm.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.usecase.GetUserDetailsUseCase
import kotlinx.coroutines.flow.*

class DetailsViewModel(
    private val getUserDetailsUseCase: GetUserDetailsUseCase,
    private val login: String
) : ViewModel() {
    val detailsFlow = flow {
        val dataFlow = getUserDetailsUseCase(login)
            .fold(
                onSuccess = { LceState.Content(it) },
                onFailure = { LceState.Error(it) }
            )
        emit(dataFlow)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )
}