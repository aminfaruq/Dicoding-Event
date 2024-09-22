package com.aminfaruq.dicodingevent.data.api

import com.aminfaruq.dicodingevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*
    * Gunakan endpoint berikut untuk mendapatkan data dari API, klik untuk melihat contoh penggunaannya.
    * Event yang aktif (akan datang):
    * https://event-api.dicoding.dev/events?active=1
    * Event yang sudah selesai:
    * https://event-api.dicoding.dev/events?active=0
    * Search event:
    * https://event-api.dicoding.dev/events?active=-1&q={keyword}
    * Detail event:
    * https://event-api.dicoding.dev/events/{id}
    * */

    /*
    * active: Int
    * 1 : Menampilkan events yang aktif/akan datang (default)
    * 0 : Menampilkan events yang sudah selesai
    * -1 : Menampilkan semua events.
    * limit: Int, default = 40
    * q: String
    * */

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