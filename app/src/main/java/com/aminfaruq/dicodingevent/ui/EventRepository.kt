package com.aminfaruq.dicodingevent.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.aminfaruq.dicodingevent.data.Result
import com.aminfaruq.dicodingevent.data.api.ApiService
import com.aminfaruq.dicodingevent.data.response.EventDetail

class EventRepository private constructor(private val apiService: ApiService) {
    fun getEvents(
        active: Int? = 1,
        query: String? = null,
        limit: Int? = 40
    ): LiveData<Result<List<EventDetail>>> = liveData {
        emit(Result.Loading) // Emit loading state
        try {
            val response = apiService.getListEvent(active, query, limit) // Repository call to fetch events
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error occurred")) // Emit error if the response indicates an error
            } else {
                response.listEvents?.let {
                    emit(Result.Success(it)) // Emit success with event list
                } ?: emit(Result.Error("No events found")) // Emit error if no events are found
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error fetching events")) // Emit error in case of failure
        }
    }

    fun getEventById(id: Int): LiveData<Result<EventDetail>> = liveData {
        emit(Result.Loading) // Emit loading state

        try {
            val response = apiService.getEventById(id) // Call the API to get the event by ID
            if (response.error == true) {
                emit(Result.Error(response.message ?: "Unknown error occurred")) // Emit error if API response indicates an error
            } else {
                emit(Result.Success(response.event)) // Emit error if no event is found in the response
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error fetching event details")) // Emit error in case of failure
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService)
            }.also { instance = it }
    }

}