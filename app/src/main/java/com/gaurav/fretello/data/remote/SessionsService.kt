package com.gaurav.fretello.data.remote

import com.gaurav.fretello.data.remote.responses.GetSessionListResponse
import retrofit2.http.GET

interface SessionsService {

    @GET("/data/sessions.json")
    suspend fun getSessionList(): GetSessionListResponse
}