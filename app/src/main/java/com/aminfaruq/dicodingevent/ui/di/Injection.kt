package com.aminfaruq.dicodingevent.ui.di

import android.content.Context
import com.aminfaruq.dicodingevent.data.api.ApiConfig
import com.aminfaruq.dicodingevent.data.room.EventsDatabase
import com.aminfaruq.dicodingevent.ui.EventRepository

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventsDatabase.getInstance(context)
        val dao = database.eventsDao()
        return EventRepository.getInstance(apiService, dao)
    }
}