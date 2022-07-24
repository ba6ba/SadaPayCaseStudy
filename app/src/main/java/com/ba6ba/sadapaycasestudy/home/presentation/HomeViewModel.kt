package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.domain.HomeUseCase
import com.ba6ba.sadapaycasestudy.managers.LightDarkModeManager
import com.ba6ba.sadapaycasestudy.managers.UiError
import com.ba6ba.sadapaycasestudy.managers.ViewState
import com.ba6ba.sadapaycasestudy.managers.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun collectPagingData(): Flow<PagingData<HomeItemUiData>> =
        homeUseCase(Unit).cachedIn(viewModelScope)

    fun setPersistedDisplayMode() {
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
        _viewStateFlow.value = when (val state = combinedLoadStates.refresh) {
            is LoadState.Loading -> ViewState.Loading
            is LoadState.Error ->
                ViewState.Error(UiError(message = state.error.message.default))
            is LoadState.NotLoading -> ViewState.Success(Unit)
        }
    }
}