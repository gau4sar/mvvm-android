package com.gaurav.fretello.data.remote

import com.gaurav.fretello.data.model.Session

class SessionRemoteDataSource(private val apiService: SessionsService) : BaseDataSource() {

    suspend fun fetchSessionList(): ApiResponseHandler<List<Session>> {
        return safeApiCall { apiService.getSessionList().toList() }
    }
}