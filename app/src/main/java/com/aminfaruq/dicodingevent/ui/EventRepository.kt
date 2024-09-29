package com.aminfaruq.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aminfaruq.dicodingevent.data.Result
import com.aminfaruq.dicodingevent.data.api.ApiService
import com.aminfaruq.dicodingevent.data.response.EventDetail
import com.aminfaruq.dicodingevent.data.room.EventsDao

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventsDao
) {

    fun getEvents(
        active: Int? = 1,
        query: String? = null,
        limit: Int? = 40
    ): LiveData<Result<List<EventDetail>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListEvent(active, query, limit)
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error occurred"))
            } else {
                response.listEvents?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("No events found"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error fetching events"))
        }
    }

    fun getEventById(id: Int): LiveData<Result<EventDetail>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.getEventById(id)
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error occurred"))
            } else {
                emit(Result.Success(response.event))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error fetching event details"))
        }
    }

    fun getFavoriteEvents(): LiveData<List<EventDetail>> = eventDao.getEvents()

    suspend fun insertFavoriteEvent(event: EventDetail) {
        eventDao.insertEvent(event)
    }

    suspend fun deleteFavoriteEvent(id: Int) {
        eventDao.deleteEventById(id)
    }

    fun isFavoriteEvent(id: Int):  LiveData<List<EventDetail>> = eventDao.getEventsById(id)

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventsDao
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }

}