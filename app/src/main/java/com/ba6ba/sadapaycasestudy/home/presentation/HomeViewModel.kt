package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.domain.HomeUseCase
import com.ba6ba.sadapaycasestudy.managers.ViewState
import com.ba6ba.sadapaycasestudy.managers.LightDarkModeManager
import com.ba6ba.sadapaycasestudy.managers.UiError
import com.ba6ba.sadapaycasestudy.managers.default
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

    val viewStateFlow: StateFlow<ViewState<Unit>>
        get() = _viewStateFlow
    private val _viewStateFlow: MutableStateFlow<ViewState<Unit>> by lazy {
        MutableStateFlow(ViewState.Idle)
    }

    val dayNightIconResource: StateFlow<Int>
        get() = _dayNightIconResource
    private val _dayNightIconResource: MutableStateFlow<Int> by lazy {
        MutableStateFlow(R.drawable.ic_day_mode)
    }

    init {
        setPersistedDisplayMode()
    }

    fun collectPagingData(): Flow<PagingData<HomeItemUiData>> =
        homeUseCase(Unit).cachedIn(viewModelScope)

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

    fun processCombinedStates(combinedLoadStates: CombinedLoadStates) {
        when (val state = combinedLoadStates.refresh) {
            is LoadState.Loading -> _viewStateFlow.value = ViewState.Loading
            is LoadState.Error -> _viewStateFlow.value =
                ViewState.Error(UiError(message = state.error.message.default))
            is LoadState.NotLoading -> _viewStateFlow.value = ViewState.Success(Unit)
        }
    }
}