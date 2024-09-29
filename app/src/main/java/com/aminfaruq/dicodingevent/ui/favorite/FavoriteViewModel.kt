package com.aminfaruq.dicodingevent.ui.favorite

import androidx.lifecycle.ViewModel
import com.aminfaruq.dicodingevent.ui.EventRepository

class FavoriteViewModel(private val repository: EventRepository) : ViewModel() {
    fun getFavoriteEvents() = repository.getFavoriteEvents()
}