package com.gaurav.fretello.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gaurav.fretello.data.model.Session

@Dao
interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSessions(sessionList: List<Session>)

    @Transaction
    @Query("SELECT * FROM sessions")
    fun getSessionList(): LiveData<List<Session>>
}