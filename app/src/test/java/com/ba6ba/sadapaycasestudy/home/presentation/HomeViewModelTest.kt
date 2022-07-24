package com.ba6ba.sadapaycasestudy.home.presentation

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.ba6ba.sadapaycasestudy.BaseTest
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.domain.HomeUseCase
import com.ba6ba.sadapaycasestudy.managers.LightDarkModeManager
import com.ba6ba.sadapaycasestudy.managers.UiError
import com.ba6ba.sadapaycasestudy.managers.ViewState
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseTest() {

    @Mock
    lateinit var lightDarkModeManager: LightDarkModeManager

    @Mock
    lateinit var homeUseCase: HomeUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(lightDarkModeManager, homeUseCase)
    }

    @Test
    fun `verify persisted display mode in case of night mode`() {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(true)

        homeViewModel.setPersistedDisplayMode()

        verify(lightDarkModeManager).setCurrentMode()
        assertEquals(homeViewModel.dayNightIconResource.value, R.drawable.ic_night_mode)
    }

    @Test
    fun `verify persisted display mode in case of day mode`() {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(false)

        homeViewModel.setPersistedDisplayMode()

        verify(lightDarkModeManager).setCurrentMode()
        assertEquals(homeViewModel.dayNightIconResource.value, R.drawable.ic_day_mode)
    }

    @Test
    fun `verify on day night button click to disable dark mode`() {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(false)

        homeViewModel.onDayNightButtonClick()

        verify(lightDarkModeManager).toggle()
        assertEquals(homeViewModel.dayNightIconResource.value, R.drawable.ic_day_mode)
    }

    @Test
    fun `verify on day night button click to enable dark mode`() {
        whenever(lightDarkModeManager.isDarkModeEnabled()).thenReturn(true)

        homeViewModel.onDayNightButtonClick()

        verify(lightDarkModeManager).toggle()
        assertEquals(homeViewModel.dayNightIconResource.value, R.drawable.ic_night_mode)
    }

    @Test
    fun `verify view state on process combined state for loading`() {
        val refreshLoadState = LoadState.Loading
        val appendLoadState = LoadState.Loading
        val prependLoadState = LoadState.Loading
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Loading)
    }

    @Test
    fun `verify view state on process combined state for error`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.Error(Throwable(message))
        val appendLoadState = LoadState.Error(Throwable(message))
        val prependLoadState = LoadState.Error(Throwable(message))
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Error(UiError(message = message)))
    }

    @Test
    fun `verify view state on process combined state for success`() {
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Success(Unit))
    }

    @Test
    fun `verify view state on process combined state for error for prepend`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.Error(Throwable(message))
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Error(UiError(message = message)))
    }

    @Test
    fun `verify view state on process combined state for error for source-prepend`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = LoadState.Error(Throwable(message)),
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Error(UiError(message = message)))
    }

    @Test
    fun `verify view state on process combined state for error for append`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.Error(Throwable(message))
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = appendLoadState,
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Error(UiError(message = message)))
    }

    @Test
    fun `verify view state on process combined state for error for source-append`() {
        val message = "some error occurred so error view must be shown"
        val refreshLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val appendLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        val prependLoadState = LoadState.NotLoading(endOfPaginationReached = false)
        homeViewModel.processCombinedStates(
            CombinedLoadStates(
                refresh = refreshLoadState,
                append = LoadState.Error(Throwable(message)),
                prepend = prependLoadState,
                mediator = null,
                source = LoadStates(refreshLoadState, prependLoadState, appendLoadState)
            )
        )

        assertEquals(homeViewModel.viewStateFlow.value, ViewState.Error(UiError(message = message)))
    }

    @Test
    fun `verify collect paging data stream`() = runTest {
        whenever(homeUseCase(Unit)).thenReturn(flowOf(PagingData.empty()))

        assertNotNull(homeViewModel.collectPagingData().take(1).firstOrNull())
    }
}