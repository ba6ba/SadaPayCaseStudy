package com.ba6ba.sadapaycasestudy.managers

import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

interface LightDarkModeManager {
    fun toggle()
    fun isDarkModeEnabled(): Boolean
    fun setCurrentMode()
}

class DefaultLightDarkModeManager @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : LightDarkModeManager {
    override fun toggle() {
        if (isDarkModeEnabled()) {
            enableLightMode()
        } else {
            enableDarkMode()
        }
    }

    override fun isDarkModeEnabled(): Boolean {
        return sharedPreferencesManager.getCurrentDisplayMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    override fun setCurrentMode() {
        if (isDarkModeEnabled()) {
            enableDarkMode()
        } else {
            enableLightMode()
        }
    }

    private fun enableDarkMode() {
        sharedPreferencesManager.setCurrentDisplayMode(AppCompatDelegate.MODE_NIGHT_YES)
        setMode()
    }

    private fun enableLightMode() {
        sharedPreferencesManager.setCurrentDisplayMode(AppCompatDelegate.MODE_NIGHT_NO)
        setMode()
    }

    private fun setMode() {
        AppCompatDelegate.setDefaultNightMode(sharedPreferencesManager.getCurrentDisplayMode())
    }
}