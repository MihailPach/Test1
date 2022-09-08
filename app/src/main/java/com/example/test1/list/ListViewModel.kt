package com.example.mvvm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.domain.repository.UserLocalRepository
import com.example.mvvm.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.channels.BufferOverflow

class ListViewModel(
    private val userLocalRepository: UserLocalRepository,
    private val useCase: GetUsersUseCase
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0

    private val loadMoreFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        onLoadMore()
    }

    val dataFlow = loadMoreFlow
        .filter { !isLoading }
        .onEach {
            isLoading = true
        }
        .map {
            useCase(currentPage * PAGE_SIZE, PAGE_SIZE)
                .fold(
                    onSuccess = {
                        userLocalRepository.insertUsers(it)
                        currentPage++
                        it
                    },
                    onFailure = {
                        emptyList()
                    }
                )
        }.onEach {
            isLoading = false
        }
//        .runningReduce { accumulator, value -> accumulator + value }
        .onStart {
            emit(userLocalRepository.getUsers().getOrDefault(emptyList()))
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    fun onLoadMore() {
        loadMoreFlow.tryEmit(Unit)
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}