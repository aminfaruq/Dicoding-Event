package com.aminfaruq.dicodingevent.data.api

import com.aminfaruq.dicodingevent.data.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getListEvent(
        @Query("active") active: Int? = 1,
        @Query("q") q: String? = null,
        @Query("limit") limit: Int? = 40
    ): EventResponse

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int
    ): EventResponse
}
