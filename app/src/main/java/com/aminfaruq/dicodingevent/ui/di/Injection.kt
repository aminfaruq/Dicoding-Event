package com.aminfaruq.dicodingevent.ui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.aminfaruq.dicodingevent.data.api.ApiConfig
import com.aminfaruq.dicodingevent.data.room.EventsDatabase
import com.aminfaruq.dicodingevent.ui.EventRepository
import com.aminfaruq.dicodingevent.ui.settings.SettingPreferences
import com.aminfaruq.dicodingevent.ui.settings.datastore

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventsDatabase.getInstance(context)
        val dao = database.eventsDao()
        val dataStore: DataStore<Preferences> = context.datastore
        val settingPreferences = SettingPreferences.getInstance(dataStore)

        return EventRepository.getInstance(apiService, dao, settingPreferences)
    }
}
