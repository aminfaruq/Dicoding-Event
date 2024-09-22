package com.aminfaruq.dicodingevent.data.api

import com.aminfaruq.dicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getListEvent(
        @Query("active") active: Int? = 1,
        @Query("q") q: String? = null,
        @Query("limit") limit: Int? = 40
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getEventById(
        @Path("id") id: Int
    ): Call<EventResponse>
}
