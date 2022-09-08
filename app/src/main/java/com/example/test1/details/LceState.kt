package com.example.mvvm.details

sealed class LceState<out T> {
    object Loading : LceState<Nothing>()
    data class Content<T>(val items: T) : LceState<T>()
    data class Error(val throwable: Throwable) : LceState<Nothing>()
}

fun <T> LceState<T>.onContent(action: (T) -> Unit) = apply {
    (this as? LceState.Content)?.items?.let(action)
}

fun <T> LceState<T>.onError(action: (Throwable) -> Unit) = apply {
    (this as? LceState.Error)?.throwable?.let(action)
}