package com.ba6ba.sadapaycasestudy

sealed class ViewState {
    object Idle : ViewState()
    object Loading : ViewState()
    data class Error(val uiError: UiError) : ViewState()
    data class Success<T>(val data: T) : ViewState()
}

data class UiError(
    val message: String
)