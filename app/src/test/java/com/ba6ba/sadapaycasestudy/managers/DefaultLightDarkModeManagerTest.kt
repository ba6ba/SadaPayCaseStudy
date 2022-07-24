package com.ba6ba.sadapaycasestudy.managers

import com.ba6ba.sadapaycasestudy.BaseTest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultLightDarkModeManagerTest : BaseTest() {

    @Mock
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    lateinit var lightDarkModeManager: LightDarkModeManager

    @Before
    fun setUp() {
        lightDarkModeManager = DefaultLightDarkModeManager(sharedPreferencesManager)
    }

    @Test
    fun `verify toggle on for day mode manager`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.NIGHT_MODE)

        lightDarkModeManager.toggle()

        verify(sharedPreferencesManager).setCurrentDisplayMode(DayNightModeConstants.DAY_MODE)
    }

    @Test
    fun `verify toggle on for night mode manager`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.DAY_MODE)

        lightDarkModeManager.toggle()

        verify(sharedPreferencesManager).setCurrentDisplayMode(DayNightModeConstants.NIGHT_MODE)
    }

    @Test
    fun `verify if dark mode enabled`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.NIGHT_MODE)

        assertTrue(lightDarkModeManager.isDarkModeEnabled())
    }

    @Test
    fun `verify if dark mode not enabled`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.DAY_MODE)

        assertFalse(lightDarkModeManager.isDarkModeEnabled())
    }

    @Test
    fun `verify set current day mode`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.DAY_MODE)

        lightDarkModeManager.setCurrentMode()

        verify(sharedPreferencesManager).setCurrentDisplayMode(DayNightModeConstants.DAY_MODE)
    }

    @Test
    fun `verify set current night mode`() {
        whenever(sharedPreferencesManager.getCurrentDisplayMode()).thenReturn(DayNightModeConstants.NIGHT_MODE)

        lightDarkModeManager.setCurrentMode()

        verify(sharedPreferencesManager).setCurrentDisplayMode(DayNightModeConstants.NIGHT_MODE)
    }
}