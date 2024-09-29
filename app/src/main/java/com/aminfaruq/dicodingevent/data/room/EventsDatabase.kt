package com.aminfaruq.dicodingevent.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aminfaruq.dicodingevent.data.response.EventDetail

@Database(entities = [EventDetail::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    companion object {
        private const val DB_NAME = "Events.db"

        @Volatile
        private var instance: EventsDatabase? = null
        fun getInstance(context: Context): EventsDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java, DB_NAME
                ).build()
            }
    }
}