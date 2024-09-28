package com.aminfaruq.dicodingevent.ui.di

import com.aminfaruq.dicodingevent.data.api.ApiConfig
import com.aminfaruq.dicodingevent.ui.EventRepository

object Injection {
    fun provideRepository(): EventRepository {
        val apiService = ApiConfig.getApiService()
        return EventRepository.getInstance(apiService)
    }
}