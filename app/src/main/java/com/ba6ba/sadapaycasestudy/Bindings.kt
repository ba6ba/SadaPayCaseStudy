package com.ba6ba.sadapaycasestudy

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["toggle_visibility"])
fun View.toggleVisibility(value: Boolean) {
    isVisible = value
}

@BindingAdapter(value = ["show_on_loading"])
fun View.showOnLoading(viewState: ViewState) {
    toggleVisibility(viewState is ViewState.Loading)
}

@BindingAdapter(value = ["show_on_error"])
fun View.showOnError(viewState: ViewState) {
    toggleVisibility(viewState is ViewState.Error)
}

@BindingAdapter(value = ["show_on_success"])
fun View.showOnSuccess(viewState: ViewState) {
    toggleVisibility(viewState is ViewState.Success<*>)
}