package com.gaurav.fretello.ui.sessions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaurav.fretello.data.model.Exercise
import com.gaurav.fretello.data.model.Session
import com.gaurav.fretello.data.remote.ApiResponseHandler
import com.gaurav.fretello.data.repository.SessionRepository
import kotlinx.coroutines.launch

class SessionsViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    val sessionList: LiveData<ApiResponseHandler<List<Session>>> =
        sessionRepository.observeSessionList

    fun saveExercises(exerciseList: List<Exercise>) {
        viewModelScope.launch {
            sessionRepository.saveSessionExercises(exerciseList)
        }
    }

    fun getExercises(sessionId: Int?): LiveData<List<Exercise>> {
        return sessionRepository.getExercisesBySessionId(sessionId)
    }
}