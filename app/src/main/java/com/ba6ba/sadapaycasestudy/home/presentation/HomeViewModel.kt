package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.domain.HomeUseCase
import com.ba6ba.sadapaycasestudy.managers.ViewState
import com.ba6ba.sadapaycasestudy.managers.LightDarkModeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lightDarkModeManager: LightDarkModeManager,
    private val homeUseCase: HomeUseCase
) : ViewModel() {

    val viewStateFlow: StateFlow<ViewState<List<HomeItemUiData>>>
        get() = _viewStateFlow
    private val _viewStateFlow: MutableStateFlow<ViewState<List<HomeItemUiData>>> by lazy {
        MutableStateFlow(ViewState.Idle)
    }

    val dayNightIconResource: StateFlow<Int>
        get() = _dayNightIconResource
    private val _dayNightIconResource: MutableStateFlow<Int> by lazy {
        MutableStateFlow(R.drawable.ic_day_mode)
    }

    init {
        viewModelScope.launch {
            delay(5000L)
            _viewStateFlow.value = ViewState.Loading

        }
        setPersistedDisplayMode()
    }

    private fun setPersistedDisplayMode() {
        lightDarkModeManager.setCurrentMode()
        updateDayNightIcon()
    }

    private fun updateDayNightIcon() {
        _dayNightIconResource.value =
            if (lightDarkModeManager.isDarkModeEnabled()) R.drawable.ic_night_mode else R.drawable.ic_day_mode
    }

    fun onDayNightButtonClick() {
        lightDarkModeManager.toggle()
        updateDayNightIcon()
    }

}