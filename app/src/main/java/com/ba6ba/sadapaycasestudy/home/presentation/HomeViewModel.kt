package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba6ba.sadapaycasestudy.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val viewStateFlow: StateFlow<ViewState>
        get() = _viewStateFlow
    private val _viewStateFlow: MutableStateFlow<ViewState> by lazy {
        MutableStateFlow(ViewState.Idle)
    }

    init {
        viewModelScope.launch {
            delay(5000L)
            _viewStateFlow.value = ViewState.Loading
        }
    }

}