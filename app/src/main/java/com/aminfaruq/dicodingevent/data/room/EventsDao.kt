package com.aminfaruq.dicodingevent.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aminfaruq.dicodingevent.data.response.EventDetail

@Dao
interface EventsDao {

    @Query("SELECT * FROM event ORDER BY id DESC")
    fun getEvents(): LiveData<List<EventDetail>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(news: EventDetail)

    @Query("DELETE FROM event WHERE id = :eventId")
    suspend fun deleteEventById(eventId: Int)

    @Query("SELECT * FROM event WHERE id=:eventId")
    fun getEventsById(eventId: Int): LiveData<List<EventDetail>>

}