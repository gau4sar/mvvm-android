package com.gaurav.fretello.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gaurav.fretello.data.local.dao.ExercisesDao
import com.gaurav.fretello.data.local.dao.SessionsDao
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.utils.Constants.FRETELLO_ROOM_DB_NAME

@Database(
    entities = [Session::class, Exercise::class],
    version = 4
)

@TypeConverters(ListConverters::class, ImageConverters::class)
abstract class FretelloRoomDb : RoomDatabase() {

    abstract fun getSessionsDao(): SessionsDao
    abstract fun getExercisesDao(): ExercisesDao

    companion object {
        private var instance: FretelloRoomDb? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also { instance = it }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FretelloRoomDb::class.java,
            FRETELLO_ROOM_DB_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }
}