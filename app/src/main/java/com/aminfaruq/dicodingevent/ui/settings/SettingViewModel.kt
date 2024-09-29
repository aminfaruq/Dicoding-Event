package com.aminfaruq.dicodingevent.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminfaruq.dicodingevent.ui.EventRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: EventRepository) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return repository.getThemeSetting()
    }

    fun saveThemeSetting(isDarkModeActivate: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActivate)
        }
    }
}