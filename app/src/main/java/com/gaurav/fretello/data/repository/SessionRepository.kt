package com.gaurav.fretello.data.repository

import androidx.lifecycle.LiveData
import com.gaurav.fretello.data.local.dao.ExercisesDao
import com.gaurav.fretello.data.local.dao.SessionsDao
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.remote.SessionRemoteDataSource
import com.gaurav.fretello.utils.responseLiveData

class SessionRepository(
    private val sessionRemoteDataSource: SessionRemoteDataSource,
    private val sessionsDao: SessionsDao,
    private val exercisesDao: ExercisesDao
) {

    val observeSessionList = responseLiveData(
        roomQueryToRetrieveData = { sessionsDao.getSessionList() },
        networkRequest = { sessionRemoteDataSource.fetchSessionList() },
        roomQueryToSaveData = { sessionsDao.insertAllSessions(it) })

    fun getExercisesBySessionId(sessionId: Int?): LiveData<List<Exercise>> {
        return exercisesDao.getExercises(sessionId)
    }

    suspend fun saveSessionExercises(exerciseList: List<Exercise>) {
        exerciseList.forEach {
            exercisesDao.insert(it)
        }
    }
}