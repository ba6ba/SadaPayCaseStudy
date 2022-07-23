package com.ba6ba.sadapaycasestudy.managers

sealed class ViewState<out T> {
    object Idle : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Error(val uiError: UiError) : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
}

data class UiError(
    val message: String
)